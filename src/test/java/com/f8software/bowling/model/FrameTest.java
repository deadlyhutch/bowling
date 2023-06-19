package com.f8software.bowling.model;

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
    void testNewState() {
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.NEW);
    }
    @Test
    void simpleStateTransitionTest() {
        frame.addBowl(1);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.ONE_BOWLED);
        frame.addBowl(2);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
    @Test
    void spareStateTransitionTest() {
        frame.addBowl(1);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.ONE_BOWLED);
        frame.addBowl(9);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), true);
        assertEquals(frame.getScoreType(), Frame.ScoreType.SPARE);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
    @Test
    void strikeStateTransitionTest() {
        frame.addBowl(10);
        assertEquals(frame.isStrike(), true);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.STRIKE);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
    @Test
    void lastSimpleStateTransitionTest() {
        frame.setLastFrame(true);
        frame.addBowl(1);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.ONE_BOWLED);
        frame.addBowl(2);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
    @Test
    void lastSpareStateTransitionTest() {
        frame.setLastFrame(true);
        frame.addBowl(1);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.NORMAL);
        assertEquals(frame.getStatus(), Frame.FrameState.ONE_BOWLED);
        frame.addBowl(9);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), true);
        assertEquals(frame.getScoreType(), Frame.ScoreType.SPARE);
        assertEquals(frame.getStatus(), Frame.FrameState.TWO_BOWLED);
        frame.addBowl(10);
        assertEquals(frame.isStrike(), false);
        assertEquals(frame.isSpare(), true);
        assertEquals(frame.getScoreType(), Frame.ScoreType.SPARE);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
    @Test
    void lastStrikeStateTransitionTest() {
        frame.setLastFrame(true);
        frame.addBowl(10);
        assertEquals(frame.isStrike(), true);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.STRIKE);
        assertEquals(frame.getStatus(), Frame.FrameState.ONE_BOWLED);
        frame.addBowl(10);
        assertEquals(frame.isStrike(), true);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.STRIKE);
        assertEquals(frame.getStatus(), Frame.FrameState.TWO_BOWLED);
        frame.addBowl(10);
        assertEquals(frame.isStrike(), true);
        assertEquals(frame.isSpare(), false);
        assertEquals(frame.getScoreType(), Frame.ScoreType.STRIKE);
        assertEquals(frame.getStatus(), Frame.FrameState.COMPLETE);
    }
}