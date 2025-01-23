package tsp.hexcore.input.registry;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.hexcore.input.Input;
import tsp.hexcore.input.parser.InputParser;
import tsp.hexcore.input.parser.InputParserRegistry;

import java.util.Deque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the registration and processing of user inputs.
 * This interface is responsible for registering inputs, processing them,
 * and handling the invalid parser scenarios.
 *
 * @author TheSilentPro (Silent)
 */
public interface InputRegistry {

    /**
     * Creates a new input registry with the specified parser registry and map.
     *
     * @param parserRegistry The registry for input parsers.
     * @param map The map implementation used for storing input entries.
     * @return A new instance of {@link InputRegistry}.
     */
    static InputRegistry newRegistry(InputParserRegistry parserRegistry, Map<UUID, Deque<Input<?>>> map) {
        return new InputRegistryImpl(parserRegistry, map);
    }

    /**
     * Creates a new input registry with the specified parser registry.
     * A default map implementation ({@link ConcurrentHashMap}) is used for storing inputs.
     *
     * @param parserRegistry The registry for input parsers.
     * @return A new instance of {@link InputRegistry}.
     */
    static InputRegistry newRegistry(InputParserRegistry parserRegistry) {
        return new InputRegistryImpl(parserRegistry, new ConcurrentHashMap<>());
    }

    /**
     * Registers an input handler that is awaiting a user response.
     *
     * @param input The input handler to register.
     * @param <T> The type of input expected (e.g., String, Integer).
     */
    <T> void register(Input<T> input);

    /**
     * Processes an input string associated with a specific input handler.
     * This method is called when an input event (e.g., a chat event) triggers the processing.
     *
     * @param id The unique identifier for the input instance.
     * @param input The input string to process.
     * @param event The event source that triggered the input processing (nullable).
     */
    void process(@NotNull UUID id, @NotNull String input, @Nullable AsyncPlayerChatEvent event);

    /**
     * Processes an input string associated with a specific input handler, without an event context.
     * This method will be called when no event source is provided.
     *
     * @param id The unique identifier for the input instance.
     * @param input The input string to process.
     */
    default void process(@NotNull UUID id, @NotNull String input) {
        process(id, input, null);
    }

    /**
     * Handler for cases where an invalid {@link InputParser} is passed to an {@link Input#await(UUID, Class)}.
     * This method is invoked when the parser registry cannot find a parser for the specified input type.
     *
     * @param type The class type of the input for which no parser was found.
     * @param <T> The type of input.
     */
    default <T> void onInvalidParser(@NotNull Class<T> type) {
        throw new IllegalArgumentException("No parser found for input type: " + type.getName());
    }
}