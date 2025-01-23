package tsp.hexcore.input;

import org.junit.jupiter.api.Test;
import tsp.hexcore.input.parser.InputParser;
import tsp.hexcore.input.parser.InputParserRegistry;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class InputParserRegistryTest {

    @Test
    void testFindParser() {
        InputParserRegistry registry = mock(InputParserRegistry.class);
        InputParser<String> stringParser = mock(InputParser.class);
        when(registry.find(String.class)).thenReturn(Optional.of(stringParser));

        Optional<InputParser<String>> result = registry.find(String.class);

        assertTrue(result.isPresent());
        assertEquals(stringParser, result.get());
    }

    @Test
    void testFindAllParsers() {
        InputParserRegistry registry = mock(InputParserRegistry.class);
        InputParser<String> stringParser = mock(InputParser.class);
        when(registry.findAll(String.class)).thenReturn(List.of(stringParser));

        List<InputParser<String>> result = registry.findAll(String.class);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(stringParser, result.get(0));
    }

    @Test
    void testRegisterParser() {
        InputParserRegistry registry = mock(InputParserRegistry.class);
        InputParser<Integer> parser = mock(InputParser.class);

        registry.register(Integer.class, parser);
        verify(registry).register(Integer.class, parser);
    }

    @Test
    void testUpdateParser() {
        InputParserRegistry registry = mock(InputParserRegistry.class);
        InputParser<Integer> parser = mock(InputParser.class);

        registry.update(Integer.class, parser);
        verify(registry).update(Integer.class, parser);
    }

}