package tsp.hexcore.input.parser;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
class InputParserRegistryImpl implements InputParserRegistry {

    private final Map<Class<?>, List<InputParser<?>>> parsers;

    InputParserRegistryImpl(Map<Class<?>, List<InputParser<?>>> map) {
        this.parsers = map;
    }

    @NotNull
    public <T> Optional<InputParser<T>> find(@NotNull Class<T> type) {
        List<InputParser<?>> parsers = this.parsers.get(type);
        if (parsers == null || parsers.isEmpty()) {
            return Optional.empty();
        }

        //noinspection unchecked,SequencedCollectionMethodCanBeUsed
        return Optional.of((InputParser<T>) parsers.get(0));
    }

    @NotNull
    public <T> List<InputParser<T>> findAll(@NotNull Class<T> type) {
        //noinspection unchecked
        List<InputParser<T>> parsers = (List<InputParser<T>>) (List<?>) this.parsers.get(type);

        if (parsers == null || parsers.isEmpty()) {
            return List.of();
        }

        return Collections.unmodifiableList(parsers);
    }

    /**
     * Register a new {@link InputParser} with the class type.
     *
     * @param type The class type for the parser
     * @param parser The parser
     * @param <T> The type
     */
    public <T> void register(@NotNull Class<T> type, @NotNull InputParser<T> parser) {
        List<InputParser<?>> list = this.parsers.computeIfAbsent(type, t -> new ArrayList<>());
        if (!list.contains(parser)) {
            list.add(parser);
        }
    }

    public <T> void update(@NotNull Class<T> type, @NotNull InputParser<T> parser) {
        List<InputParser<?>> list = this.parsers.computeIfAbsent(type, t -> new ArrayList<>());
        if (list.contains(parser)) {
            list.set(list.indexOf(parser), parser);
        }
    }

}
