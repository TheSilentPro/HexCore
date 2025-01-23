package tsp.hexcore.input;

import org.junit.jupiter.api.Test;
import tsp.hexcore.util.DurationParser;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class DurationParserTest {

    @Test
    void testParseValidDuration() {
        assertEquals(Duration.of(2, ChronoUnit.DAYS), DurationParser.parse("2d"));
        assertEquals(Duration.of(3, ChronoUnit.HOURS), DurationParser.parse("3h"));
        assertEquals(Duration.of(4, ChronoUnit.MINUTES), DurationParser.parse("4m"));
        assertEquals(Duration.of(5, ChronoUnit.SECONDS), DurationParser.parse("5s"));
    }

    @Test
    void testParseMultipleDurations() {
        assertEquals(Duration.of(2, ChronoUnit.DAYS).plus(Duration.of(3, ChronoUnit.HOURS)), DurationParser.parse("2d 3h"));
        assertEquals(Duration.of(2, ChronoUnit.DAYS).plus(Duration.of(5, ChronoUnit.MINUTES)), DurationParser.parse("2d 5m"));
    }

    @Test
    void testParseInvalidDuration() {
        assertNull(DurationParser.parse("2x")); // Unsupported unit
        assertNull(DurationParser.parse("abc")); // Invalid string
        assertNull(DurationParser.parse("")); // Empty string
    }

    @Test
    void testParseSafely() {
        assertTrue(DurationParser.parseSafely("2d").isPresent());
        assertFalse(DurationParser.parseSafely("abc").isPresent());
    }

    @Test
    void testParseZeroDuration() {
        assertNull(DurationParser.parse("0d"));
    }

    @Test
    void testParseNull() {
        assertNotNull(DurationParser.parseSafely(null));
    }

}