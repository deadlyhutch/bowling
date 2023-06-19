package com.f8software.bowling.render;

import com.f8software.bowling.model.Frame;
import com.f8software.bowling.model.Player;

import java.util.List;

/**
 * This class is used to Render the scores for a Player. The Player object is all that is needed
 * to render the scores from each Frame contained by the Player
 */
public class StandardOutRenderer implements Renderer {
    private static final int MAX_FRAMES = 10;
    private static final int MAX_PINS = 10;
    private static final String FRAME_ROW_START = "Frame  : ";
    private static final String BOWLS_ROW_START = "Bowls  : ";
    private static final String SCORE_ROW_START = "Score  : ";
    private static final String FRAME_HEADER = "   -%s- ";
    private static final String SCORE_TEMPLATE = "   %3s ";
    private static final String STRIKE_TEMPLATE = "   X - ";
    private static final String STRIKE = "X";
    private static final String SPARE = "/";
    private static final String LAST_FRAME_STRIKE_TEMPLATE = "   X %s %s";
    private static final String SPARE_TEMPLATE = "   %s / ";
    private static final String LAST_FRAME_SPARE_TEMPLATE = "   %s / %s";
    private static final String BOWL_START = "  ";
    private static final String BOWL_TEMPLATE = " %s";
    private static final String BOWL_END= " ";
    private static final String BLANK = "";
    private static final String HORIZONTAL_LINE = "-".repeat(80);
    private StringBuilder builder;

    /**
     * This method is responsible for rendering the player scores to the
     * standard output.
     * Output will be:
     * - Frame Headers row : displays the frame number
     * - Bowls Row : displays the results of each bowl
     * - Score Row : displays the running score for each frame
     * @param player The player whose scores are to be rendered
     */
    @Override
    public void render(Player player) {
        builder = new StringBuilder();
        List<Frame> frames = player.getFrames();

        System.out.println(HORIZONTAL_LINE);

        renderFrameHeaderRow();
        System.out.print(renderBowlsRow(builder, frames));
        renderScoreRow(frames);

        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Renders ths Frame Header Row
     */
    private void renderFrameHeaderRow() {
        System.out.print(FRAME_ROW_START);
        for (int frameNum= 1; frameNum <= MAX_FRAMES; frameNum++) {
            System.out.print(FRAME_HEADER.formatted(frameNum));
        }
    }

    /**
     * Renders the Bowl Row
     * @param frames The Frames that have been played so far
     */
    private StringBuilder renderBowlsRow(StringBuilder builder, List<Frame> frames) {
        builder.append("\n");
        builder.append(BOWLS_ROW_START);
        for (Frame frame : frames) {
            renderBowls(builder, frame);
        }
        return builder;
    }

    /**
     * Renders the scores for each Frame
     * @param frames
     */
    private void renderScoreRow(List<Frame> frames) {
        System.out.println();
        System.out.print(SCORE_ROW_START);
        for (Frame frame : frames) {
            System.out.print(SCORE_TEMPLATE.formatted(frame.getScore()));
        }
        System.out.println();
    }

    /**
     * Renders the individual bowl scores
     * @param frame
     */
    private StringBuilder renderBowls(StringBuilder builder, Frame frame) {
        switch (frame.getScoreType()) {
            case STRIKE -> {
                if (frame.isLastFrame()) {
                    renderLastFrameStrike(builder, frame);
                } else {
                    builder.append(STRIKE_TEMPLATE);
                }
            }
            case SPARE -> {
                if (frame.isLastFrame()) {
                    renderLastFrameSpare(builder, frame);
                } else {
                    builder.append(SPARE_TEMPLATE.formatted(frame.getFirstBowlScore()));
                }
            }
            case NORMAL -> {
                List<Integer> bowls = frame.getBowls();
                builder.append(BOWL_START);
                for (Integer bowl : bowls) {
                    builder.append(BOWL_TEMPLATE.formatted(bowl));
                }
                builder.append(BOWL_END);
            }
        }
        return builder;
    }

    /**
     * Renders the last Frame of the game as it has some special case logic for a Spare
     * @param frame
     */
    private StringBuilder renderLastFrameSpare(StringBuilder builder, Frame frame) {
        if (frame.getStatus() == Frame.FrameState.COMPLETE) {
            int third = frame.getThirdBowlScore();
            builder.append(LAST_FRAME_SPARE_TEMPLATE.formatted(frame.getFirstBowlScore(), third == 10 ? STRIKE : third));
        } else {
            builder.append(SPARE_TEMPLATE.formatted(frame.getFirstBowlScore()));
        }
        return builder;
    }

    /**
     * Renders the last Frame of the game as it has some special case logic for a Strike
     * @param frame
     */
    private StringBuilder renderLastFrameStrike(StringBuilder builder, Frame frame) {
        switch (frame.getStatus()) {
            case COMPLETE -> {
                int second = frame.getSecondBowlScore();
                int third  = frame.getThirdBowlScore();
                if (second + third == MAX_PINS) {
                    builder.append(LAST_FRAME_STRIKE_TEMPLATE.formatted(second == MAX_PINS ? STRIKE : second, SPARE));
                } else {
                    builder.append(LAST_FRAME_STRIKE_TEMPLATE.formatted(second == MAX_PINS ? STRIKE : second, third == MAX_PINS ? STRIKE : third));
                }
            }
            case TWO_BOWLED -> {
                int second = frame.getSecondBowlScore();
                builder.append(LAST_FRAME_STRIKE_TEMPLATE.formatted( second == MAX_PINS ? STRIKE : second, BLANK));
            }
            default -> {
                builder.append(LAST_FRAME_STRIKE_TEMPLATE.formatted( BLANK, BLANK));
            }
        }
        return builder;
    }
}
