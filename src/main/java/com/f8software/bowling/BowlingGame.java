package com.f8software.bowling;

import com.f8software.bowling.input.BowlingCLI;
import com.f8software.bowling.input.InputProvider;
import com.f8software.bowling.model.Frame;
import com.f8software.bowling.model.Player;
import com.f8software.bowling.render.Renderer;
import com.f8software.bowling.render.StandardOutRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class can be used to keep track of bowling scores for a single player game.
 * It is responsible for capturing input from the input provider, passing the inout to the player
 * and then passing the player to the rendered to be displayed.
 */
public class BowlingGame {
    final static Logger logger = LogManager.getLogger(BowlingGame.class);
    public static final int MAX_FRAMES = 10;
    private final Player player = new Player();
    private final InputProvider inputProvider = new BowlingCLI();
    private final Renderer renderer = new StandardOutRenderer();

    public static void main(String[] args) {
        BowlingGame bowlingGame = new BowlingGame();
        bowlingGame.play();
    }

    /**
     * This method will play the game of bowling up to the maximum number of Frames - receiving bowls from the
     * inputProvider and after each bowl will pass the player to the renderer to be displayed.
     * The player will keep track of frames and scores after each state change (bowl)
     *
     * If an invalid bowl is entered it will be ignored and the player may enter again.
     */
    public void play() {
        logger.info("Starting Bowling Game");
        // Display initial blank scorecard
        renderer.render(player);
        // Play the required number of Frames
        for (int frameNum = 1; frameNum <= MAX_FRAMES; frameNum++) {
            Frame frame = player.getCurrentFrame();
            while (frame.getStatus() != Frame.FrameState.COMPLETE) {
                boolean added = false;
                // Bowls may be rejected if invalid
                while (!added) {
                    int bowl = inputProvider.getInput();
                    added = player.addBowl(bowl);
                }
                // Display the updated scores
                renderer.render(player);
            }
        }
        logger.info("Game Over");
    }
}
