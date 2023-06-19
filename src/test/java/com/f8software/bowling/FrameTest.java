package src.test.java.com.f8software.bowling;

import com.f8software.bowling.model.Frame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    Frame frame;

    @BeforeEach
    void setUp() {
        frame = new Frame();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void basicScoreTest() {
        frame.addBowl(4);
        frame.addBowl(3);

        assertEquals(7, frame.getScore());
    }

    void noScoreTest() {
        frame.addBowl(0);
        frame.addBowl(0);

        assertEquals(0, frame.getScore());
    }

    void negativeScoreTest() {;
        assertThrows(IllegalArgumentException.class,
                () -> {
                    frame.addBowl(-1);
                });
    }
}