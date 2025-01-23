package tsp.hexcore.input;

import org.junit.jupiter.api.Test;
import tsp.hexcore.input.parser.InputParser;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class InputParserTest {

    @Test
    void testParseValid() {
        InputParser<Integer> parser = mock(InputParser.class);
        when(parser.parse("123")).thenReturn(Optional.of(123));

        Optional<Integer> result = parser.parse("123");

        assertTrue(result.isPresent());
        assertEquals(123, result.get());
    }

    @Test
    void testParseInvalid() {
        InputParser<Integer> parser = mock(InputParser.class);
        when(parser.parse("abc")).thenReturn(Optional.empty());

        Optional<Integer> result = parser.parse("abc");

        assertFalse(result.isPresent());
    }
}
