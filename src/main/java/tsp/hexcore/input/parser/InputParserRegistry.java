package tsp.hexcore.input.parser;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import tsp.hexcore.util.DurationParser;
import tsp.hexcore.util.NumberParser;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry that manages type-based {@link InputParser}s.
 * It allows finding, registering, and updating parsers for various types of inputs.
 *
 * @author TheSilentPro (Silent)
 */
public interface InputParserRegistry {

    /**
     * Creates a new {@link InputParserRegistry} with the provided map for storing parsers.
     *
     * @param map The map implementation used to store parsers, keyed by input types.
     * @return A new instance of {@link InputParserRegistry}.
     */
    static InputParserRegistry newRegistry(Map<Class<?>, List<InputParser<?>>> map) {
        return new InputParserRegistryImpl(map);
    }

    /**
     * Creates a new {@link InputParserRegistry} with a default map implementation.
     *
     * @return A new instance of {@link InputParserRegistry}.
     */
    static InputParserRegistry newRegistry() {
        return new InputParserRegistryImpl(new ConcurrentHashMap<>());
    }

    /**
     * Finds a parser for the specified type.
     *
     * @param type The class type of the input that needs to be parsed.
     * @param <T> The type of the parsed value.
     * @return An {@link Optional} containing the parser, if present.
     */
    @NotNull <T> Optional<InputParser<T>> find(@NotNull Class<T> type);

    /**
     * Finds all parsers for the specified type.
     *
     * @param type The class type of the input that needs to be parsed.
     * @param <T> The type of the parsed value.
     * @return A list of parsers that handle the specified type, which may be empty if none are found.
     */
    @NotNull <T> List<InputParser<T>> findAll(@NotNull Class<T> type);

    /**
     * Registers a new {@link InputParser} for the specified class type.
     *
     * @param type The class type for the parser.
     * @param parser The parser to register.
     * @param <T> The type of the parsed value.
     */
    <T> void register(@NotNull Class<T> type, @NotNull InputParser<T> parser);

    /**
     * Updates an existing {@link InputParser} for the specified class type.
     * This operation is marked as experimental and may change in future releases.
     *
     * @param type The class type for the parser.
     * @param parser The parser to update.
     * @param <T> The type of the parsed value.
     * @since 1.0
     */
    @ApiStatus.Experimental
    <T> void update(@NotNull Class<T> type, @NotNull InputParser<T> parser);

    /**
     * Registers a set of default parsers for common types such as String, Integer, Boolean, Duration, etc.
     * This method is automatically invoked to populate the registry with commonly used parsers.
     *
     * @return The {@link InputParserRegistry} instance with the default parsers registered.
     */
    default InputParserRegistry registerDefaults() {
        register(String.class, Optional::of);
        register(Number.class, NumberParser::parse);
        register(Integer.class, NumberParser::parseInteger);
        register(Long.class, NumberParser::parseLong);
        register(Double.class, NumberParser::parseDouble);
        register(Float.class, NumberParser::parseFloat);
        register(Byte.class, NumberParser::parseByte);
        register(Boolean.class, s -> {
            if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")) {
                return Optional.of(true);
            } else if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("No") || s.equalsIgnoreCase("off")) {
                return Optional.of(false);
            } else {
                return Optional.empty();
            }
        });
        register(Duration.class, DurationParser::parseSafely);
        register(UUID.class, s -> {
            try {
                return Optional.of(UUID.fromString(s));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        });
        return this;
    }

}