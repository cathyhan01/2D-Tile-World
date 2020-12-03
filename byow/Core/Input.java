package byow.Core;

import byow.TileEngine.TETile;

/**
 * Models what a generic input handler should be able to do.
 * @source Hug - InputSource.java
 */
public interface Input {
    char getNextChar();
    char getPrevChar();
    void setPlaying();
    void setWorld(TETile[][] w);
    boolean hasNextInput();
}
