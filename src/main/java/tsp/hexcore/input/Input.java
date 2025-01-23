package tsp.hexcore.input;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import tsp.hexcore.input.registry.InputRegistry;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents a generic input handler that manages awaiting and processing user input.
 * The input can be associated with specific handlers for successful input, mismatches,
 * expiration events, and more. The input operation has an optional expiration time and
 * can be registered in an input registry.
 *
 * @param <T> The type of input expected (e.g., String, Integer, etc.)
 * @author TheSilentPro (Silent)
 */
public interface Input<T> {

    /**
     * Creates a new input instance that awaits user input of the specified type.
     *
     * @param id The unique identifier for the input instance.
     * @param requiredInputType The class type of the expected input.
     * @param <T> The type of the expected input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    static <T> Input<T> await(UUID id, Class<T> requiredInputType) {
        return new BaseInput<>(id, requiredInputType);
    }

    default Input<String> awaitString() {
        return await(getId(), String.class).register(getRegistry());
    }

    default Input<Number> awaitNumber() {
        return await(getId(), Number.class).register(getRegistry());
    }

    default Input<Integer> awaitInteger() {
        return await(getId(), Integer.class).register(getRegistry());
    }

    default Input<Long> awaitLong() {
        return await(getId(), Long.class).register(getRegistry());
    }

    default Input<Double> awaitDouble() {
        return await(getId(), Double.class).register(getRegistry());
    }

    default Input<Float> awaitFloat() {
        return await(getId(), Float.class).register(getRegistry());
    }

    default Input<Byte> awaitByte() {
        return await(getId(), Byte.class).register(getRegistry());
    }

    default Input<Boolean> awaitBoolean() {
        return await(getId(), Boolean.class).register(getRegistry());
    }

    /**
     * Creates a new input instance that awaits user input of the specified type.
     *
     * @param registry The registry that will hold this input.
     * @param id The unique identifier for the input instance.
     * @param requiredInputType The class type of the expected input.
     * @param <U> The type of the expected input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default <U> Input<U> await(InputRegistry registry, UUID id, Class<U> requiredInputType) {
        return await(id, requiredInputType).register(registry);
    }

    /**
     * Creates a new input instance that awaits user input of the specified type.
     *
     * @param registry The registry that will hold this input.
     * @param requiredInputType The class type of the expected input.
     * @param <U> The type of the expected input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default <U> Input<U> await(InputRegistry registry, Class<U> requiredInputType) {
        return await(registry, getId(), requiredInputType);
    }

    /**
     * Creates a new input instance that awaits user input of the same type.
     *
     * @param registry The registry that will hold this input.
     * @param id The unique identifier for the input instance.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default Input<T> await(InputRegistry registry, UUID id) {
        return await(registry, id, getRequiredInputType());
    }

    /**
     * Creates a new input instance that awaits user input of the same type.
     *
     * @param registry The registry that will hold this input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default Input<T> await(InputRegistry registry) {
        return await(registry, getId(), getRequiredInputType()).register(registry);
    }

    /**
     * Creates a new input instance that awaits user input of the specified type.
     *
     * @param requiredInputType The class type of the expected input.
     * @param <U> The type of the expected input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default <U> Input<U> await(Class<U> requiredInputType) {
        return await(getId(), requiredInputType);
    }

    /**
     * Creates a new input instance that awaits user input.
     *
     * @param input The input to wait for.
     * @param <U> The type of the expected input.
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default <U> Input<U> await(Input<U> input) {
        return input;
    }

    /**
     * Creates a new input instance that awaits user input of the same type.
     *
     * @return A new {@link Input} instance that is awaiting user input.
     */
    default Input<T> await() {
        return await(getId(), getRequiredInputType());
    }

    /**
     * Sets a maximum duration that the input will remain active, after which it expires.
     *
     * @param duration The duration to wait before the input expires.
     * @return The updated input instance with the expiration time set.
     */
    Input<T> until(Duration duration);

    /**
     * Sets a handler to be executed when the expected input is successfully provided.
     *
     * @param handler The handler that processes the input.
     * @return The updated input instance with the handler applied.
     */
    Input<T> then(Consumer<T> handler);

    /**
     * Sets a handler that is executed when the expected input is provided, with
     * additional context from an {@link AsyncPlayerChatEvent}.
     *
     * @param handler The handler that processes the input and the event.
     * @return The updated input instance with the handler applied.
     */
    Input<T> then(BiConsumer<T, AsyncPlayerChatEvent> handler);

    /**
     * Sets a handler to be executed when the input does not match the expected format.
     *
     * @param handler The handler that processes the mismatch event.
     * @return The updated input instance with the mismatch handler applied.
     */
    Input<T> mismatch(Consumer<String> handler);

    /**
     * Sets a handler to be executed when the input has expired.
     *
     * @param handler The handler that processes the expiration event.
     * @return The updated input instance with the expiration handler applied.
     */
    Input<T> expired(Consumer<String> handler);

    /**
     * Sets the timestamp at which the input was created.
     *
     * @param timestamp The timestamp.
     * @return The updated input instance with the timestamp.
     * @see #hasExpired()
     */
    Input<T> timestamp(Instant timestamp);

    /**
     * Sets the timestamp at which the input was created.
     *
     * @return The updated input instance with the timestamp.
     * @see #hasExpired()
     */
    default Input<T> timestamp() {
        return timestamp(Instant.now());
    }

    /**
     * Indicates that expired inputs should be ignored, preventing the expiration handler
     * from being triggered.
     *
     * @return The updated input instance with expired inputs ignored.
     */
    Input<T> ignoreExpired();

    /**
     * Registers the input with a given registry, which will manage the input state.
     *
     * @param registry The registry to register the input with.
     * @return The updated input instance.
     */
    Input<T> register(InputRegistry registry);

    // Getters

    /**
     * Gets the unique identifier for the input.
     *
     * @return The UUID that identifies the input instance.
     */
    UUID getId();

    /**
     * Gets the duration for which the input is valid, or {@code null} if no expiration
     * duration has been set.
     *
     * @return The duration the input will remain active.
     */
    Duration getDuration();

    /**
     * Gets the timestamp when the input was created.
     *
     * @return The instant when the input was created.
     */
    Instant getTimestamp();

    /**
     * Gets the handler that processes the successful input.
     *
     * @return The consumer that handles the expected input.
     */
    Consumer<T> getInputHandler();

    /**
     * Gets the handler that processes the successful input with an additional
     * {@link AsyncPlayerChatEvent}.
     *
     * @return The bi-consumer that handles the input and the event.
     */
    BiConsumer<T, AsyncPlayerChatEvent> getBiInputHandler();

    /**
     * Gets the handler that processes a mismatch in the input.
     *
     * @return The consumer that handles the mismatch event.
     */
    Consumer<String> getMismatchHandler();

    /**
     * Gets the handler that processes an expired input.
     *
     * @return The consumer that handles the expiration event.
     */
    Consumer<String> getExpiredHandler();

    /**
     * Gets the class type of the expected input.
     *
     * @return The class type of the input.
     */
    Class<T> getRequiredInputType();

    /**
     * Checks if the input has expired.
     *
     * @return {@code true} if the input has expired, {@code false} otherwise.
     */
    boolean hasExpired();

    /**
     * Checks if expired inputs should be ignored.
     *
     * @return {@code true} if expired inputs are ignored, {@code false} otherwise.
     */
    boolean shouldIgnoreExpired();

    /**
     * Gets the {@link InputRegistry} for this input.
     *
     * @return The registry
     */
    InputRegistry getRegistry();

}