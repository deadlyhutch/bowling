package src.main.java.com.f8software.bowling.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The Frame class is used to model the events and scores in a Frame of bowls
 * After each bowl the state will be updated. A Player contains a list of Frames.
 */
public class Frame {

    private final static Logger logger = LogManager.getLogger(Frame.class);
    private FrameState state = FrameState.NEW;
    private ScoreType scoreType = ScoreType.NORMAL;
    private final List<Integer> bowls = new ArrayList<>();

    private static final int MAX_PINS = 10;
    private int score;
    private boolean isLastFrame;

    /**
     * Frame states are mutually exclusive so are modeled using an Enum
     */
    public enum FrameState {NEW, ONE_BOWLED, TWO_BOWLED, COMPLETE}

    /**
     * Score types are mutually exclusive so are modeled using an Enum
     */
    public enum ScoreType {NORMAL, SPARE, STRIKE}

    /**
     * Return the bowls for the Frame, this could have 0, 1, 2 or 3 depending
     * on the state of the Frame
     * @return
     */
    public List<Integer> getBowls() {
        return bowls;
    }

    /**
     * Returns true if this is the last frame of the game. The last frame requires special
     * treatment as it has different rules in the game
     * @return
     */
    public boolean isLastFrame() {
        return isLastFrame;
    }

    /**
     * This is set to true when it's the last Frame of the game
     * @param isLastFrame
     */
    public void setLastFrame(boolean isLastFrame) {
        this.isLastFrame = isLastFrame;
    }

    /**
     * Called by the Player when bowling in this Frame
     * @param bowl this is the number of pins knocked down - can be zero
     * @return
     */
    public boolean addBowl(int bowl) {
        boolean valid = validate(bowl);
        if (valid) {
            bowls.add(bowl);
            updateState(bowl);
        } else {
            logger.warn("Invalid bowl, there are only %s pins left".formatted(getPinsLeft()));
        }
        return valid;
    }

    /**
     * Utility method to return any score of the first bowl
     * If the bowl has not taken place yet, zero will be returned
     * @return The score of the first bowl - can be zero
     */
    public int getFirstBowlScore() {
        return getBowlScore(0);
    }
    /**
     * Utility method to return any score of the second bowl
     * If the bowl has not taken place yet, zero will be returned
     * @return The score of the second bowl - can be zero
     */
    public int getSecondBowlScore() {
        return getBowlScore(1);
    }
    /**
     * Utility method to return any score of the third bowl
     * If the bowl has not taken place yet, zero will be returned
     * @return The score of the third bowl - can be zero
     */
    public int getThirdBowlScore() {
        return getBowlScore(2);
    }

    /**
     * This method is called when state changes - typically this would be when a bowl is
     * added to this frame.
     * A state update will transition between the states in @{@link FrameState}
     * A mutually exclusive score type will be updated to a type in @{@link ScoreType}
     * @param bowled the number of pins bowled by the player
     */
    private void updateState(int bowled) {
        if (state == FrameState.COMPLETE) {
            throw new IllegalStateException("You cannot add a bowl after Frame has completed");
        }

        if (!isLastFrame) {
            updateStateForFrame(bowled);
        } else {
            updateStateForLastFrame(bowled);
        }
    }
    /**
     * Handles the state transition of Frames that are not the last of the game
     */
    private void updateStateForFrame (int bowled) {
        switch (state) {
            case NEW -> {
                if (bowled == MAX_PINS) {
                    scoreType = ScoreType.STRIKE;
                    state = FrameState.COMPLETE;
                } else {
                    state = FrameState.ONE_BOWLED;
                }
            }
            case ONE_BOWLED -> {
                if (totalBowled() == MAX_PINS) {
                    scoreType = ScoreType.SPARE;
                }
                state = FrameState.COMPLETE;
            }
        }
    }
    /**
     * Handles state transitions for the last Frame of a game
     */
    private void updateStateForLastFrame(int bowled) {
        switch (state) {
            case NEW -> {
                if (bowled == MAX_PINS) {
                    scoreType = ScoreType.STRIKE;
                }
                state = FrameState.ONE_BOWLED;
            }
            case ONE_BOWLED -> {
                if (totalBowled() < MAX_PINS) {
                    state = FrameState.COMPLETE;
                } else {
                    if (totalBowled() == MAX_PINS) {
                        scoreType = ScoreType.SPARE;
                    }
                    state = FrameState.TWO_BOWLED;
                }
            }
            case TWO_BOWLED -> state = FrameState.COMPLETE;
        }
    }

    /**
     * The Player will update the score for the Frame - since a Player contains the full
     * list of Frames for the game, the Player will update the score after each state change (bowl)
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the current score for the Frame
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current state of the Frame from @{@link FrameState}
     * @return the frame state from @{@link FrameState}
     */
    public FrameState getStatus() {
        return state;
    }

    /**
     * Returns the score type of the Frame from @{@link ScoreType}
     * @return The score type from @{@link ScoreType}
     */
    public ScoreType getScoreType() {
        return scoreType;
    }

    /**
     * Return the total of all the bowls in this Frame
     * @return
     */
    public int totalBowled() {
        int total = 0;
        for (Integer bowl : bowls) {
            total += bowl;
        }
        return total;
    }

    /**
     * Return true if the Frame is a strike
     * @return true if frame is a Strike
     */
    public boolean isStrike() {
        return scoreType == ScoreType.STRIKE;
    }

    /**
     * Returns true if the Frame is a spare
     * @return true if the frame is a Spare
     */
    public boolean isSpare() {
        return scoreType == ScoreType.SPARE;
    }

    /**
     * Returns the score for a bowl from this frame if it has taken place.
     * If the bowl has not taken place then zero is returned
     * @param i
     * @return
     */
    private int getBowlScore(int i) {
        if (i < bowls.size()) {
            Integer bowl = bowls.get(i);
            if (bowl != null) {
                return bowl;
            }
        }
        return 0;
    }

    /**
     * Calculates the pins left to knockdown in this frame. This is used to validate the
     * bowls being added to this Frame
     * @return
     */
    private int getPinsLeft() {
        if (isLastFrame && (isSpare() || isStrike())) {
            if (isDoubleStrike() || isSpare()) {
                return MAX_PINS;
            } else {
                return MAX_PINS - getSecondBowlScore();
            }
        } else {
            return MAX_PINS - totalBowled();
        }
    }

    /**
     * A special case test for a double strike in the last Frame - used to validate the pins left when
     * adding bowls to this Frame
     * @return
     */
    private boolean isDoubleStrike() {
        return isLastFrame
                && isStrike()
                && state == FrameState.TWO_BOWLED
                && getSecondBowlScore() == MAX_PINS;
    }

    /**
     * Used to validate bowls being added to this Frame
     * @param bowl
     * @return True is the bowl is a valid value and not below zero of above the remaining pins in th Frame
     */
    private boolean validate(int bowl) {
        return bowl >= 0 && bowl <= getPinsLeft();
    }
}
