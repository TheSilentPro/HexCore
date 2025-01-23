package tsp.hexcore.input;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.Nullable;
import tsp.hexcore.input.registry.InputRegistry;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class BaseInput<T> implements Input<T> {

    private final UUID id;
    private final Class<T> requiredInputType;
    private InputRegistry registry;
    private Instant createdAt;
    private Duration duration;
    private Consumer<T> handler;
    private BiConsumer<T, AsyncPlayerChatEvent> biHandler;
    private Consumer<String> mismatchHandler;
    private Consumer<String> expiredHandler;
    private boolean ignoreExpired;

    public BaseInput(UUID id, Class<T> requiredInputType) {
        this.id = id;
        this.requiredInputType = requiredInputType;
        this.createdAt = Instant.now();
        this.ignoreExpired = false;
    }

    @Override
    public Input<T> until(Duration duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public Input<T> then(Consumer<T> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public Input<T> then(BiConsumer<T, AsyncPlayerChatEvent> handler) {
        this.biHandler = handler;
        return this;
    }

    @Override
    public Input<T> mismatch(Consumer<String> handler) {
        this.mismatchHandler = handler;
        return this;
    }

    @Override
    public Input<T> expired(Consumer<String> handler) {
        this.expiredHandler = handler;
        return this;
    }

    @Override
    public Input<T> register(InputRegistry registry) {
        registry.register(this);
        this.registry = registry;
        return this;
    }

    @Override
    public Input<T> timestamp(Instant timestamp) {
        this.createdAt = timestamp;
        return this;
    }

    @Override
    public Input<T> ignoreExpired() {
        this.ignoreExpired = true;
        return this;
    }

    // Getters

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Instant getTimestamp() {
        return createdAt;
    }

    @Override
    public Consumer<T> getInputHandler() {
        return handler;
    }

    @Override
    public BiConsumer<T, AsyncPlayerChatEvent> getBiInputHandler() {
        return biHandler;
    }

    @Override
    public Consumer<String> getMismatchHandler() {
        return mismatchHandler;
    }

    @Override
    public Consumer<String> getExpiredHandler() {
        return expiredHandler;
    }

    @Override
    public Class<T> getRequiredInputType() {
        return requiredInputType;
    }

    @Override
    public boolean hasExpired() {
        if (duration != null) {
            return Instant.now().isAfter(createdAt.plus(duration));
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldIgnoreExpired() {
        return ignoreExpired;
    }

    @Override
    @Nullable
    public InputRegistry getRegistry() {
        return registry;
    }

}
