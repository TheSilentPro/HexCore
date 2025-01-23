package tsp.hexcore.input;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.junit.jupiter.api.Test;
import tsp.hexcore.input.registry.InputRegistry;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class InputRegistryTest {

    @Test
    void testRegister() {
        UUID id = UUID.randomUUID();
        Input<String> input = mock(Input.class);
        InputRegistry registry = mock(InputRegistry.class);

        registry.register(input);
        verify(registry).register(input);
    }

    @Test
    void testProcess() {
        UUID id = UUID.randomUUID();
        InputRegistry registry = mock(InputRegistry.class);
        AsyncPlayerChatEvent event = mock(AsyncPlayerChatEvent.class);

        registry.process(id, "Test Input", event);
        verify(registry).process(id, "Test Input", event);
    }

    @Test
    void testOnInvalidParser() {
        InputRegistry registry = mock(InputRegistry.class);
        Class<String> type = String.class;

        doThrow(IllegalArgumentException.class).when(registry).onInvalidParser(type);

        assertThrows(IllegalArgumentException.class, () -> registry.onInvalidParser(type));
    }

}