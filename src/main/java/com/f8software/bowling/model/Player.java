package src.main.java.com.f8software.bowling.model;

import com.f8software.bowling.BowlingGame;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class is used to hold the bowling activity and will keep
 * track of bowling frames and scores.
 * After each state change (bowl) the Frame scores will be updated
 */
public class Player {
    private final List<Frame> frames = new ArrayList<>();
    private Frame currentFrame;

    /**
     * Returns the Frames that have been played or started so far in this game
     * Can be used by a renderer for example to display the status
     * @return List of Frames
     */
    public List<Frame> getFrames() {
        return frames;
    }

    /**
     * This method is used to add a new Frame to the game
     * @param frame
     */
    private void addFrame(Frame frame) {
        frames.add(frame);
        currentFrame = frame;
        if (frames.size() == BowlingGame.MAX_FRAMES) {
            frame.setLastFrame(true);
        }
    }

    /**
     * This method is called by the Player to update the scores of each Frame
     * after each state change (bowl)
     */
    public void updateScores() {
        int total = 0;
        for (int i = 0; i < frames.size() ; i++) {
            Frame current = frames.get(i);
            int frameScore;
            // We know that we have at least 10 if we have a strike or spare
            if (current.isStrike() || current.isSpare()) {
                frameScore = 10;
            } else {
                frameScore = current.totalBowled();
            }
            // We can also add more if the next bowl is known
            if (current.isSpare()) {
                if (current.isLastFrame()) {
                    frameScore += current.getThirdBowlScore();
                } else {
                    frameScore += getNextBowlScore(i + 1);
                }
            }
            // For a strike we need to look ahead to see if we have more to add
            if (current.isStrike()) {
                // First look ahead to see if we have any scores yet
                if (current.isLastFrame()) {
                    frameScore += current.getSecondBowlScore();
                    frameScore += current.getThirdBowlScore();
                } else {
                    Frame next = lookAhead(i + 1);
                    if (next != null) {
                        if (next.isLastFrame()) {
                            frameScore += next.getFirstBowlScore();
                            frameScore += next.getSecondBowlScore();
                        } else {
                            if (next.isStrike()) {
                                frameScore += 10;
                                // Second look ahead
                                Frame nextAgain = lookAhead(i + 2);
                                if (nextAgain != null) {
                                    frameScore += nextAgain.getFirstBowlScore();
                                }
                            } else {
                                frameScore += next.totalBowled();
                            }
                        }
                    }
                }
            }
            total += frameScore;
            current.setScore(total);
        }
    }

    /**
     * Utility method used to score a strike or spare and will lookahead to see if the Frame ahead
     * has been played yet - if not it will return null
     * @param i - internal index of the frame
     * @return The frame if it exist at that index or null if not
     */
    private Frame lookAhead(int i) {
        if (i < frames.size()) {
            return frames.get(i);
        }
        return null;
    }
    private int getNextBowlScore(int i) {
        Frame next = lookAhead(i);
        if (next != null) {
            return next.getFirstBowlScore();
        }
        return 0;
    }

    /**
     * This method will try to add a bowl to the player.
     * If the bowl is rejected because it is invalid then this method will
     * return false, otherwise true
     * @param bowl
     * @return true if successfully added to the player
     */
    public boolean addBowl(int bowl) {
        boolean added = getCurrentFrame().addBowl(bowl);
        if (added) {
            updateScores();
        }
        return added;
    }

    /**
     * Used to retrieve a list of current scores for each of the Frames that have been played or started
     * @return List of current scores for each Frame that exists in this game
     */
    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        for (Frame frame : frames) {
            scores.add(frame.getScore());
        }
        return scores;
    }

    /**
     * Returns the current frame being played for the game
     * @return the current Frame
     */
    public Frame getCurrentFrame() {
        if (currentFrame == null || currentFrame.getStatus() == Frame.FrameState.COMPLETE) {
            addFrame(new Frame());
        }
        return currentFrame;
    }
}
