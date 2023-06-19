package com.f8software.bowling.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Command Line Interface used to input bowls played by a player in a game of
 * ten pin bowling
 */
public class BowlingCLI implements InputProvider {

    private static int MAX_TRIES = 3;
    private static final String MESSAGE = "How many pins were knocked down?";
    private static final String INVALID_MESSAGE = "Invalid input, please enter a number";
    final static Logger logger = LogManager.getLogger(BowlingCLI.class);

    /**
     * This method is used to record bowl counts from a ten pin bowling player.
     * Each input is a integer representing the number of bowls that were knocked down.
     * If garbage is entered then retries will be attempted before throwing an Exception
     * @return An integer representing the number of pins knocked down.
     */
    @Override
    public int getInput() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        int tries = 0;
        while (tries < MAX_TRIES) {
            try {
                System.out.println(MESSAGE);
                input = scanner.nextInt();
                break;
            } catch (Exception exception) {
                logger.warn(INVALID_MESSAGE);
                scanner.nextLine();
                tries++;
            }
        }
        if (tries == MAX_TRIES) {
            throw new IllegalArgumentException("Exceeded retires for invalid entry");
        }
        return input;
    }
}
