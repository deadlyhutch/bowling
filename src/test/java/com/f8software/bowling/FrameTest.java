package com.f8software.bowling;

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
    void basicBowledTest() {
        frame.addBowl(4);
        frame.addBowl(3);

        assertEquals(7, frame.totalBowled());
    }

    @Test
    void noBowledTest() {
        frame.addBowl(0);
        frame.addBowl(0);

        assertEquals(0, frame.totalBowled());
    }

    @Test
    void invalidBowlTests() {;
        assertEquals(false, frame.addBowl(-1));
        assertEquals(true, frame.addBowl(1));
        assertEquals(false, frame.addBowl(11));
    }
}