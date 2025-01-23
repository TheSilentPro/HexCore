package tsp.hexcore.input.registry;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.hexcore.input.Input;
import tsp.hexcore.input.parser.InputParserRegistry;

import java.util.Deque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
class InputRegistryImpl implements InputRegistry {

    private final InputParserRegistry parserRegistry;
    private final Map<UUID, Deque<Input<?>>> inputs;

    InputRegistryImpl(InputParserRegistry parserRegistry, Map<UUID, Deque<Input<?>>> map) {
        this.parserRegistry = parserRegistry;
        this.inputs = map;
    }

    @Override
    public <T> void register(Input<T> input) {
        inputs.computeIfAbsent(input.getId(), k -> new ConcurrentLinkedDeque<>()).add(input);
    }

    @Override
    public void process(@NotNull UUID id, @NotNull String input, @Nullable AsyncPlayerChatEvent event) {
        Deque<Input<?>> registeredInputs = inputs.get(id);
        if (registeredInputs == null) {
            return;
        }

        if (!registeredInputs.isEmpty()) {
            // Poll the first input in the queue
            Input<?> registeredInput = registeredInputs.pollFirst();
            if (registeredInput != null) {
                //noinspection CodeBlock2Expr
                parserRegistry.find(registeredInput.getRequiredInputType())
                        .ifPresentOrElse(parser -> {
                            parser.parse(input).ifPresentOrElse(parsedInput -> {
                                if (registeredInput.hasExpired() && !registeredInput.shouldIgnoreExpired()) {
                                    if (registeredInput.getExpiredHandler() != null) {
                                        registeredInput.getExpiredHandler().accept(input);
                                    }
                                    return;
                                }

                                if (registeredInput.getInputHandler() != null) {
                                    //noinspection unchecked
                                    Consumer<Object> handler = (Consumer<Object>) registeredInput.getInputHandler();
                                    handler.accept(parsedInput);
                                }

                                if (event != null) {
                                    if (registeredInput.getBiInputHandler() != null) {
                                        //noinspection unchecked
                                        BiConsumer<Object, AsyncPlayerChatEvent> handler = (BiConsumer<Object, AsyncPlayerChatEvent>) registeredInput.getBiInputHandler();
                                        handler.accept(parsedInput, event);
                                    }
                                }
                            }, () -> {
                                if (registeredInput.getMismatchHandler() != null) {
                                    registeredInput.getMismatchHandler().accept(input);
                                }
                            });
                        }, () -> onInvalidParser(registeredInput.getRequiredInputType()));
            }
        }

        // Clean up the registry if no inputs remain for the given ID
        if (registeredInputs.isEmpty()) {
            inputs.remove(id);
        }
    }

}
