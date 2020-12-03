package byow.Core;

import byow.TileEngine.TETile;

/**
 * Handles string input.
 * @source Hug - StringInputDevice.java
 */
public class StringInput implements Input {

    private String userInput;
    private char prevChar;
    private int index;

    public StringInput(String str) {
        userInput = str.trim().toLowerCase();
        prevChar = ' ';
        index = 0;
    }

    @Override
    public void setPlaying() {
        throw new IllegalArgumentException("This method should not be called!");
    }

    @Override
    public void setWorld(TETile[][] w) {
        throw new IllegalArgumentException("This method should not be called!");
    }

    /**
     * Gets the next input character.
     * @return      the most recently inputted character
     */
    @Override
    public char getNextChar() {
        if (index > 0) {
            prevChar = userInput.charAt(index - 1);
        }
        char c = userInput.charAt(index);
        index++;
        return c;
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
        return (index < userInput.length());
    }
}
