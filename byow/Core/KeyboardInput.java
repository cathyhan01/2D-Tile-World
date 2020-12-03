package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Handles keyboard input.
 * @source Hug - KeyboardInputSource.java
 */
public class KeyboardInput implements Input {

    private char prevChar;
    private char currChar;
    private UIElements ui;
    private TETile[][] world;
    private boolean isPlaying;

    public KeyboardInput(UIElements u) {
        prevChar = ' ';
        currChar = ' ';
        ui = u;
        isPlaying = false;
    }

    /**
     * Sets the TETile world we're playing in to the given.
     * @param w     the given TETile world
     */
    public void setWorld(TETile[][] w) {
        world = w;
    }

    /**
     * Sets us to a state of playing the game(past the main menu stage).
     */
    public void setPlaying() {
        isPlaying = true;
    }

    /**
     * Gets the next input character.
     * @return      the most recently inputted character
     */
    @Override
    public char getNextChar() {
        while (true) {
            ui.drawHover(world, isPlaying);
            if (StdDraw.hasNextKeyTyped()) {
                prevChar = currChar;
                currChar = StdDraw.nextKeyTyped();
                return Character.toLowerCase(currChar);
            }
        }
    }

    /**
     * Gets the previously entered character.
     * @return      the previous character
     */
    @Override
    public char getPrevChar() {
        return prevChar;
    }

    /**
     * Checks whether we have more incoming input.
     * @return      true if there is next input, false otherwise
     */
    @Override
    public boolean hasNextInput() {
        return true;
    }
}
