package com.f8software.bowling.render;

import com.f8software.bowling.model.Frame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardOutRendererTest {

    StandardOutRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new StandardOutRenderer();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSpareFrameRender() {
        boolean isLast = false;
        assertEquals("   3 / ", playLastFrame(Arrays.asList(3, 7), isLast));
    }

    @Test
    void testInvalidSpareFrameRender() {
        boolean isLast = false;
        assertEquals("   3 / ", playLastFrame(Arrays.asList(3, 20, 7), isLast));
    }

    @Test
    void testOneBowledFrameRender() {
        boolean isLast = false;
        assertEquals("   3 ", playLastFrame(Arrays.asList(3), isLast));
    }

    @Test
    void testTwoBowledFrameRender() {
        boolean isLast = false;
        assertEquals("   3 4 ", playLastFrame(Arrays.asList(3,4), isLast));
    }

    @Test
    void testZeroBowledFrameRender() {
        boolean isLast = false;
        assertEquals("   0 0 ", playLastFrame(Arrays.asList(0, 0), isLast));
    }

    @Test
    void testStrikeFrameRender() {
        boolean isLast = false;
        assertEquals("   X - ", playLastFrame(Arrays.asList(10), isLast));
    }

    @Test
    void testStrikeLastFrameRender() {
        boolean isLast = true;
        assertEquals("   X  ", playLastFrame(Arrays.asList(10), isLast));
        assertEquals("   X X X", playLastFrame(Arrays.asList(10, 10, 10), isLast));
        assertEquals("   X X 0", playLastFrame(Arrays.asList(10, 10, 0), isLast));
        assertEquals("   X X 3", playLastFrame(Arrays.asList(10, 10, 3), isLast));
        assertEquals("   X 0 /", playLastFrame(Arrays.asList(10, 0, 10), isLast));
        assertEquals("   X 0 0", playLastFrame(Arrays.asList(10, 0, 0), isLast));
        assertEquals("   X 0 3", playLastFrame(Arrays.asList(10, 0, 3), isLast));
        assertEquals("   X 3 0", playLastFrame(Arrays.asList(10, 3, 0), isLast));
        assertEquals("   X 3 1", playLastFrame(Arrays.asList(10, 3, 1), isLast));
        assertEquals("   X 3 3", playLastFrame(Arrays.asList(10, 3, 3), isLast));
    }

    @Test
    void testDoubleStrikeLastFrameRender() {
        boolean isLast = true;
        assertEquals("   X X ", playLastFrame(Arrays.asList(10, 10), isLast));
        assertEquals("   X X 1", playLastFrame(Arrays.asList(10, 10, 1), isLast));
    }

    @Test
    void testLowFirstLastFrameRender() {
        boolean isLast = true;
        assertEquals("   3 5 ", playLastFrame(Arrays.asList(3, 5), isLast));
        assertEquals("   3 0 ", playLastFrame(Arrays.asList(3, 0), isLast));
        assertEquals("   3 / X", playLastFrame(Arrays.asList(3, 7, 10), isLast));
        assertEquals("   3 / 0", playLastFrame(Arrays.asList(3, 7, 0), isLast));
        assertEquals("   3 / X", playLastFrame(Arrays.asList(3, 7, 10), isLast));
        assertEquals("   3 / 3", playLastFrame(Arrays.asList(3, 7, 3), isLast));
    }

    @Test
    void testZeroFirstLastFrameRender() {
        boolean isLast = true;
        assertEquals("   0 0 ", playLastFrame(Arrays.asList(0, 0), isLast));
        assertEquals("   0 3 ", playLastFrame(Arrays.asList(0, 3), isLast));
        assertEquals("   0 / X", playLastFrame(Arrays.asList(0, 10, 10), isLast));
        assertEquals("   0 / 0", playLastFrame(Arrays.asList(0, 10, 0), isLast));
        assertEquals("   0 / 3", playLastFrame(Arrays.asList(0, 10, 3), isLast));
    }

    @Test
    void testSpareLastFrameRender() {
        boolean isLast = true;
        assertEquals("   5 / ", playLastFrame(Arrays.asList(5, 5), isLast));
    }

    /**
     * Utility test method to play a frame of a game
     * @param bowls
     * @return
     */
    private String playLastFrame(List<Integer> bowls, boolean isLast) {
        StringBuilder builder = new StringBuilder();
        Frame frame = new Frame();
        frame.setLastFrame(isLast);
        for (Integer bowl : bowls) {
            frame.addBowl(bowl);
        }
        renderer.renderBowls(builder, frame);
        return builder.toString();
    }
}
