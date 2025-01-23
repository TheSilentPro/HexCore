package tsp.hexcore.input;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheSilentPro (Silent)
 */
class InputTest {

    @Test
    void testAwait() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);

        assertNotNull(input);
        assertEquals(id, input.getId());
        assertEquals(String.class, input.getRequiredInputType());
    }

    @Test
    void testUntil() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);
        Duration duration = Duration.ofSeconds(10);
        Input<String> result = input.until(duration);

        assertEquals(duration, result.getDuration());
    }

    @Test
    void testThenHandler() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);
        Consumer<String> handler = mock(Consumer.class);

        input.then(handler);
        input.getInputHandler().accept("Test");

        verify(handler).accept("Test");
    }

    @Test
    void testMismatchHandler() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);
        Consumer<String> mismatchHandler = mock(Consumer.class);

        input.mismatch(mismatchHandler);
        input.getMismatchHandler().accept("Mismatch");

        verify(mismatchHandler).accept("Mismatch");
    }

    @Test
    void testExpiredHandler() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);
        Consumer<String> expiredHandler = mock(Consumer.class);

        input.expired(expiredHandler);
        input.getExpiredHandler().accept("Expired");

        verify(expiredHandler).accept("Expired");
    }

    @Test
    void testIgnoreExpired() {
        UUID id = UUID.randomUUID();
        Input<String> input = Input.await(id, String.class);
        Input<String> result = input.ignoreExpired();

        assertTrue(result.shouldIgnoreExpired());
    }
}

