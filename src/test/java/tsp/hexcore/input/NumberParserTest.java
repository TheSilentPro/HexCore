package tsp.hexcore.input;

import org.junit.jupiter.api.Test;
import tsp.hexcore.util.NumberParser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class NumberParserTest {

    @Test
    void testParse() {
        // Test parsing different number types
        assertTrue(NumberParser.parse("123").isPresent());
        assertTrue(NumberParser.parse("123.45").isPresent());
        assertTrue(NumberParser.parse("-123.45").isPresent());
        assertFalse(NumberParser.parse("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parse("").isPresent()); // Empty string
        assertFalse(NumberParser.parse(null).isPresent()); // Null input
    }

    @Test
    void testParseInteger() {
        // Test parsing integer
        assertTrue(NumberParser.parseInteger("123").isPresent());
        assertFalse(NumberParser.parseInteger("123.45").isPresent()); // Not an integer
        assertFalse(NumberParser.parseInteger("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parseInteger("").isPresent()); // Empty string
        assertFalse(NumberParser.parseInteger(null).isPresent()); // Null input
    }

    @Test
    void testParseLong() {
        // Test parsing long
        assertTrue(NumberParser.parseLong("123456789012345").isPresent());
        assertFalse(NumberParser.parseLong("123.45").isPresent()); // Not a long
        assertFalse(NumberParser.parseLong("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parseLong("").isPresent()); // Empty string
        assertFalse(NumberParser.parseLong(null).isPresent()); // Null input
    }

    @Test
    void testParseFloat() {
        // Test parsing float
        assertTrue(NumberParser.parseFloat("123.45").isPresent());
        assertTrue(NumberParser.parseFloat("-123.45").isPresent());
        assertFalse(NumberParser.parseFloat("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parseFloat("").isPresent()); // Empty string
        assertFalse(NumberParser.parseFloat(null).isPresent()); // Null input
    }

    @Test
    void testParseDouble() {
        // Test parsing double
        assertTrue(NumberParser.parseDouble("123.45").isPresent());
        assertTrue(NumberParser.parseDouble("-123.45").isPresent());
        assertFalse(NumberParser.parseDouble("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parseDouble("").isPresent()); // Empty string
        assertFalse(NumberParser.parseDouble(null).isPresent()); // Null input
    }

    @Test
    void testParseByte() {
        // Test parsing byte
        assertTrue(NumberParser.parseByte("127").isPresent()); // Max byte value
        assertTrue(NumberParser.parseByte("-128").isPresent()); // Min byte value
        assertFalse(NumberParser.parseByte("128").isPresent()); // Out of byte range
        assertFalse(NumberParser.parseByte("abc").isPresent()); // Invalid string
        assertFalse(NumberParser.parseByte("").isPresent()); // Empty string
        assertFalse(NumberParser.parseByte(null).isPresent()); // Null input
    }
}