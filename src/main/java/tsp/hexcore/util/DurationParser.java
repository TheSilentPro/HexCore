package tsp.hexcore.util;

import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * A utility class that provides methods for parsing durations from string representations.
 * The supported formats for the duration include combinations of years, months, weeks, days,
 * hours, minutes, and seconds.
 */
public final class DurationParser {

    private static final Map<String, ChronoUnit> UNIT_MAP = Map.ofEntries(
            Map.entry("w", ChronoUnit.WEEKS),
            Map.entry("week", ChronoUnit.WEEKS),
            Map.entry("weeks", ChronoUnit.WEEKS),
            Map.entry("d", ChronoUnit.DAYS),
            Map.entry("day", ChronoUnit.DAYS),
            Map.entry("days", ChronoUnit.DAYS),
            Map.entry("halfdays", ChronoUnit.HALF_DAYS),
            Map.entry("halfday", ChronoUnit.HALF_DAYS),
            Map.entry("h", ChronoUnit.HOURS),
            Map.entry("hour", ChronoUnit.HOURS),
            Map.entry("hours", ChronoUnit.HOURS),
            Map.entry("m", ChronoUnit.MINUTES),
            Map.entry("minute", ChronoUnit.MINUTES),
            Map.entry("minutes", ChronoUnit.MINUTES),
            Map.entry("s", ChronoUnit.SECONDS),
            Map.entry("second", ChronoUnit.SECONDS),
            Map.entry("seconds", ChronoUnit.SECONDS),
            Map.entry("millis", ChronoUnit.MILLIS),
            Map.entry("milliseconds", ChronoUnit.MILLIS),
            Map.entry("micros", ChronoUnit.MICROS),
            Map.entry("microseconds", ChronoUnit.MICROS),
            Map.entry("nanos", ChronoUnit.NANOS),
            Map.entry("nanoseconds", ChronoUnit.NANOS),
            // Manually implemented due them not being fixed length. (leap years and such...)
            Map.entry("mo", ChronoUnit.MONTHS),
            Map.entry("month", ChronoUnit.MONTHS),
            Map.entry("months", ChronoUnit.MONTHS),
            Map.entry("y", ChronoUnit.YEARS),
            Map.entry("year", ChronoUnit.YEARS),
            Map.entry("years", ChronoUnit.YEARS),
            Map.entry("decade", ChronoUnit.DECADES),
            Map.entry("decades", ChronoUnit.DECADES),
            Map.entry("century", ChronoUnit.CENTURIES),
            Map.entry("centuries", ChronoUnit.CENTURIES),
            Map.entry("millennium", ChronoUnit.MILLENNIA),
            Map.entry("millennia", ChronoUnit.MILLENNIA),
            Map.entry("era", ChronoUnit.ERAS),
            Map.entry("forever", ChronoUnit.FOREVER)
    );

    private static final Pattern SPLIT = Pattern.compile(" ");

    /**
     * Parses the given input string into a {@link Duration}.
     * The input string is expected to represent a duration with valid time units (e.g., "1d 2h 3m").
     * It can contain days, hours, minutes, and seconds, with optional spaces between values.
     *
     * @param input the input string representing the duration
     * @return the parsed {@link Duration} object, or null if the input string cannot be parsed into a valid duration
     */
    @Nullable
    public static Duration parse(@Nullable String input, @Nullable Consumer<String> invalidPartHandler, @Nullable Consumer<String> invalidNumberPartHandler) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        Duration duration = Duration.ZERO;
        int length = input.length();
        int i = 0;

        while (i < length) {
            // Skip whitespace before the number part
            while (i < length && Character.isWhitespace(input.charAt(i))) {
                i++;
            }

            if (i >= length) {
                break;
            }

            // Capture the number part
            int numberStart = i;
            while (i < length && Character.isDigit(input.charAt(i))) {
                i++;
            }

            if (i == numberStart) {
                // No number found, handle the invalid part
                if (invalidNumberPartHandler != null) {
                    invalidNumberPartHandler.accept(input.substring(i));
                }
                break;
            }

            String numberStr = input.substring(numberStart, i);
            int number = Integer.parseInt(numberStr);

            // Skip whitespace after the number part
            while (i < length && Character.isWhitespace(input.charAt(i))) {
                i++;
            }

            // Capture the unit part
            int unitStart = i;
            while (i < length && Character.isLetter(input.charAt(i))) {
                i++;
            }

            String unit = input.substring(unitStart, i).toLowerCase();

            // Look up the unit in the map
            ChronoUnit unitType = UNIT_MAP.get(unit);
            if (unitType != null) {
                // Add the corresponding unit to the duration
                duration = duration.plus(unitType.getDuration().multipliedBy(number));
            } else {
                // If the unit is invalid, handle it
                if (invalidPartHandler != null) {
                    invalidPartHandler.accept(input.substring(unitStart, i));
                }
            }

            // Skip whitespace after the unit part and continue processing
            while (i < length && Character.isWhitespace(input.charAt(i))) {
                i++;
            }
        }

        return duration.isZero() ? null : duration;
    }

    public static Duration parse(@Nullable String input, @Nullable Consumer<String> invalidPartHandler) {
        return parse(input, invalidPartHandler, null);
    }

    public static Duration parse(@Nullable String input) {
        return parse(input, null, null);
    }

    /**
     * Safely attempts to parse a given input string into a {@link Duration}, wrapping the result in an
     * {@link Optional}. If the parsing fails (for example, due to an invalid format), it returns an
     * empty {@link Optional} instead of throwing an exception.
     *
     * @param input the input string representing the duration
     * @return an {@link Optional} containing the parsed {@link Duration}, or an empty {@link Optional} if parsing fails
     */
    public static Optional<Duration> parseSafely(@Nullable String input,  @Nullable Consumer<String> invalidPartHandler, @Nullable Consumer<String> invalidNumberPartHandler) {
        try {
            return Optional.ofNullable(parse(input, invalidPartHandler, invalidNumberPartHandler));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    public static Optional<Duration> parseSafely(@Nullable String input,  @Nullable Consumer<String> invalidPartHandler) {
        return parseSafely(input, invalidPartHandler, null);
    }

    public static Optional<Duration> parseSafely(@Nullable String input) {
        return parseSafely(input, null, null);
    }

}
