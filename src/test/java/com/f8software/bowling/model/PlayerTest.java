package src.test.java.com.f8software.bowling.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void maximumTest() {
        List<Integer> input = Collections.nCopies(12, 10);
        for (Integer bowl : input) {
            player.addBowl(bowl);
        }
        List<Integer> scores = player.getScores();
        List<Integer> expected = Arrays.asList(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);

        assertEquals(expected, scores);
    }

    @Test
    void zeroTest() {
        List<Integer> input = Collections.nCopies(20, 0);
        for (Integer bowl : input) {
            player.addBowl(bowl);
        }
        List<Integer> scores = player.getScores();
        List<Integer> expected = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        assertEquals(expected, scores);
    }

    @Test
    void allSpareTest() {
        List<Integer> input = Collections.nCopies(21, 5);
        for (Integer bowl : input) {
            player.addBowl(bowl);
        }
        List<Integer> scores = player.getScores();
        List<Integer> expected = Arrays.asList(15, 30, 45, 60, 75, 90, 105, 120, 135, 150);

        assertEquals(expected, scores);
    }

    @Test
    void realisticMixtureTest() {
        // Frame 1
        player.addBowl(3);
        assertEquals(Arrays.asList(3), player.getScores());
        player.addBowl(4);
        assertEquals(Arrays.asList(7), player.getScores());
        // Frame 2 - Spare
        player.addBowl(5);
        assertEquals(Arrays.asList(7, 12), player.getScores());
        player.addBowl(5);
        assertEquals(Arrays.asList(7, 17), player.getScores());
        // Frame 3 - Strike
        player.addBowl(10);
        assertEquals(Arrays.asList(7, 27, 37), player.getScores());
        // Frame 4 - Spare
        player.addBowl(3);
        assertEquals(Arrays.asList(7, 27, 40, 43), player.getScores());
        player.addBowl(7);
        assertEquals(Arrays.asList(7, 27, 47, 57), player.getScores());
        // Frame 5
        player.addBowl(6);
        assertEquals(Arrays.asList(7, 27, 47, 63, 69), player.getScores());
        player.addBowl(3);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72), player.getScores());
        // Frame 6 - Spare
        player.addBowl(8);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 80), player.getScores());
        player.addBowl(2);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 82), player.getScores());
        // Frame 7 - Strike
        player.addBowl(10);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 102), player.getScores());
        // Frame 8 - Spare
        player.addBowl(6);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 108, 114), player.getScores());
        player.addBowl(4);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 122), player.getScores());
        // Frame 9
        player.addBowl(2);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 124, 126), player.getScores());
        player.addBowl(4);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 124, 130), player.getScores());
        // Frame 10 - Strike
        player.addBowl(10);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 124, 130, 140), player.getScores());
        player.addBowl(3);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 124, 130, 143), player.getScores());
        player.addBowl(3);
        assertEquals(Arrays.asList(7, 27, 47, 63, 72, 92, 112, 124, 130, 146), player.getScores());
    }

    /**
     * Tests 9 zero frames then combinations for the final frame
     */
    @Test
    void lastFrameTest() {
        List<Integer> setup = Collections.nCopies(18, 0);

        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 30), runGame(setup, Arrays.asList(10, 10, 10)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 10), runGame(setup, Arrays.asList(10, 0, 0)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 20), runGame(setup, Arrays.asList(10, 10, 0)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 24), runGame(setup, Arrays.asList(10, 10, 4)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 20), runGame(setup, Arrays.asList(10, 4, 6)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 19), runGame(setup, Arrays.asList(10, 4, 5)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 20), runGame(setup, Arrays.asList(4, 6, 10)));
        assertEquals(Arrays.asList(0,0,0,0,0,0,0,0,0, 13), runGame(setup, Arrays.asList(0, 10, 3)));

    }

    List<Integer> runGame(List<Integer> setup, List<Integer> end) {
        Player player = new Player();
        for (Integer bowl : setup) {
            player.addBowl(bowl);
        }
        for (Integer bowl : end) {
            player.addBowl(bowl);
        }
        return player.getScores();
    }

    @Test
    void strikeTest() {
        player.addBowl(10);
        assertEquals(Arrays.asList(10), player.getScores());
        player.addBowl(10);
        assertEquals(Arrays.asList(20, 30), player.getScores());
        player.addBowl(10);
        assertEquals(Arrays.asList(30, 50, 60), player.getScores());
    }

    @Test
    void spareTest() {
        player.addBowl(2);
        assertEquals(Arrays.asList(2), player.getScores());
        player.addBowl(8);
        assertEquals(Arrays.asList(10), player.getScores());
        player.addBowl(5);
        assertEquals(Arrays.asList(15, 20), player.getScores());
    }

    @Test
    void ordinaryTest() {
        player.addBowl(2);
        assertEquals(Arrays.asList(2), player.getScores());
        player.addBowl(7);
        assertEquals(Arrays.asList(9), player.getScores());
    }

    @Test
    void spareOrdinarySpareTest() {
        // Spare
        player.addBowl(2);
        assertEquals(Arrays.asList(2), player.getScores());
        player.addBowl(8);
        assertEquals(Arrays.asList(10), player.getScores());
        // Ordinary
        player.addBowl(5);
        assertEquals(Arrays.asList(15, 20), player.getScores());
        player.addBowl(3);
        assertEquals(Arrays.asList(15, 23), player.getScores());
        // Spare
        player.addBowl(5);
        assertEquals(Arrays.asList(15, 23, 28), player.getScores());
        player.addBowl(5);
        assertEquals(Arrays.asList(15, 23, 33), player.getScores());
        // Next bowl
        player.addBowl(6);
        assertEquals(Arrays.asList(15, 23, 39, 45), player.getScores());
    }

    @Test
    void strikeOrdinaryStrikeTest() {
        // Strike
        player.addBowl(10);
        assertEquals(Arrays.asList(10), player.getScores());
        // Ordinary
        player.addBowl(2);
        assertEquals(Arrays.asList(12, 14), player.getScores());
        player.addBowl(6);
        assertEquals(Arrays.asList(18, 26), player.getScores());
        // Strike
        player.addBowl(10);
        assertEquals(Arrays.asList(18, 26, 36), player.getScores());
        // Next bowl
        player.addBowl(6);
        assertEquals(Arrays.asList(18, 26, 42, 48), player.getScores());
        player.addBowl(1);
        assertEquals(Arrays.asList(18, 26, 43, 50), player.getScores());
    }

}