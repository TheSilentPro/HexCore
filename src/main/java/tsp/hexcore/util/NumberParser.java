package tsp.hexcore.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

/**
 * A utility class that provides methods for parsing different numeric types from a string representation.
 * The parsing methods return an {@link Optional} to handle invalid inputs gracefully, returning an
 * empty {@link Optional} if parsing fails.
 * <p>
 * This class includes methods for parsing {@link Number}, {@link Integer}, {@link Long}, {@link Float},
 * {@link Double}, and {@link Byte}. Each method attempts to parse a given string and returns an {@link Optional}
 * containing the parsed value if successful, or an empty {@link Optional} if the string cannot be parsed.
 * </p>
 *
 * @author TheSilentPro (Silent)
 */
public final class NumberParser {

    // Private constructor to prevent instantiation
    private NumberParser() {
        throw new UnsupportedOperationException("Utility class.");
    }

    /**
     * Attempts to parse the given string into a {@link Number}. This method can parse any number type, such as
     * integers, floating-point numbers, etc.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed number if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Number> parse(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(NumberFormat.getInstance().parse(s));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Attempts to parse the given string into an {@link Integer}.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed integer if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Integer> parseInteger(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Attempts to parse the given string into a {@link Long}.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed long if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Long> parseLong(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Attempts to parse the given string into a {@link Float}.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed float if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Float> parseFloat(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Float.parseFloat(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Attempts to parse the given string into a {@link Double}.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed double if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Double> parseDouble(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Attempts to parse the given string into a {@link Byte}.
     *
     * @param s the string to parse
     * @return an {@link Optional} containing the parsed byte if successful, or an empty {@link Optional} if parsing fails
     */
    @NotNull
    public static Optional<Byte> parseByte(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Byte.parseByte(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}