package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * Handles all of the drawing involved in the game.
 * Draws components such as the main menu, instructions,
 * game stats, HUD, and reactive behavior according to the game state.
 * @author Cathy Han, Annabelle Raven
 */
public class UIElements {

    private int score;
    private int minesLeft;
    private int livesLeft;
    private String name;
    private String theme;
    private int width;
    private int height;
    private int prevX;
    private int prevY;
    private boolean userMoved;
    private boolean usingKeyboard;

    public UIElements(int w, int h, boolean keyboardInput) {
        score = 0;
        minesLeft = 5;
        livesLeft = 3;
        name = "Anonymous Player";
        theme = "default";
        width = w;
        height = h;
        userMoved = false;
        usingKeyboard = keyboardInput;
        if (usingKeyboard) {
            StdDraw.setCanvasSize(width * 16, height * 16);
            StdDraw.setXscale(0, width);
            StdDraw.setYscale(0, height);
            StdDraw.enableDoubleBuffering();
        }
    }

    /**
     * Returns whether we are using keyboard input or not.
     * @return      true if we're using keyboard input, false otherwise
     */
    public boolean getUsingKeyboard() {
        return usingKeyboard;
    }

    /**
     * Sets whether the user is using keyboard input to the given boolean.
     * @param b     the given boolean
     */
    public void setUsingKeyboard(boolean b) {
        usingKeyboard = b;
    }

    /**
     * Sets whether the user's mouse has moved to the given boolean.
     * @param b     the given boolean
     */
    public void setUserMoved(boolean b) {
        userMoved = b;
    }

    /**
     * Update user stats when the user has stepped on a mine tile.
     * Draws a new interface as the user encounters/interacts with a mine tile.
     * @param isLoading     whether we're loading the game or not
     */
    public void useMine(boolean isLoading) {
        if (usingKeyboard) {
            if (!isLoading) {
                StdDraw.filledRectangle(width / 2, height / 2, 40, 15);
                StdDraw.setPenColor(Color.RED);
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
                StdDraw.text(width / 2, height - 7, "Oh No! You Have "
                        + "Stepped On A Mine & Lost A Life");
                StdDraw.picture(width / 2, height / 3, "byow/Core/images/proj3_explosion.jpeg");
                StdDraw.show(1500);
            }
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 14));
        }
        minesLeft--;
        livesLeft--;
    }

    /**
     * Draws the user's current game states, including user lives, mines left, score, player name.
     * Once the user has run out of lives, a game over interface will be drawn.
     */
    public void drawGameStats() {
        if (usingKeyboard) {
            StdDraw.setPenColor(new Color(149, 185, 189));
            StdDraw.filledRectangle(width / 2, height - 1, 40, 1);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 15));
            if (livesLeft > 0) {
                StdDraw.textLeft(2, height - 1, "Mines Left: " + minesLeft);
                StdDraw.text((width / 3) * 2, height - 1, "Score: " + score);
                StdDraw.textRight(width - 2, height - 1, "Lives Left: " + livesLeft);
                StdDraw.text(width / 3, height - 1, "Player Name: " + name);
            } else {
                StdDraw.filledRectangle(width / 2, height / 2, 40, 15);
                StdDraw.setPenColor(Color.RED);
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
                StdDraw.text(width / 2, height / 2, "GAME OVER. You ran out of lives :(");
                StdDraw.show(1500);
                System.exit(1);
            }
            StdDraw.show();
        }
    }

    /**
     * Increments the user's score with the appropriate value when the user steps on
     * a golden coin or golden egg tile. Draws a new interface as the user
     * encounters/interacts with a gold tile.
     * @param isEgg         whether the tile is a golden egg or not
     * @param isLoading     whether we're loading a game or not
     */
    public void incrementScore(boolean isEgg, boolean isLoading) {
        if (usingKeyboard) {
            StdDraw.filledRectangle(width / 2, height / 2, 40, 15);
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
        }
        if (isEgg) {
            score += 15;
            if (!isLoading && usingKeyboard) {
                StdDraw.text(width / 2, height - 7, "Congratulations! You Have Found A Golden Egg");
                StdDraw.picture(width / 2, height / 3, "byow/Core/images/proj3_egg.jpeg");
                StdDraw.show(1500);
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 14));
            }
        } else {
            score += 2;
            if (!isLoading && usingKeyboard) {
                StdDraw.text(width / 2, height - 7, "Congratulations! "
                        + "You Have Found A Golden Coin");
                StdDraw.picture(width / 2, height / 3, "byow/Core/images/proj3_coin.jpeg");
                StdDraw.show(1500);
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 14));
            }
        }
    }

    /**
     * Draws the hover part of the HUD in the game. Whatever the user's mouse
     * is hovering over, a description of that tile is drawn.
     * @param world     the tile world we're playing in
     * @param enable    whether we want to enable HUD or not
     */
    public void drawHover(TETile[][] world, boolean enable) {
        if (enable && usingKeyboard) {
            int mouseX = (int) Math.floor(StdDraw.mouseX());
            int mouseY = (int) Math.floor(StdDraw.mouseY());
            TETile tile = world[mouseX][mouseY];
            if (prevX != mouseX || prevY != mouseY || userMoved) {
                prevX = mouseX;
                prevY = mouseY;
                setUserMoved(false);
                StdDraw.setPenColor(new Color(149, 185, 189));
                StdDraw.filledRectangle(width / 2, height - 1, 5, 1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.show();
                StdDraw.setFont(new Font("Monaco", Font.PLAIN, 14));
                StdDraw.text(width / 2, height - 1, "Tile: " + tile.description());
                StdDraw.show();
            }
        }
    }

    /**
     * Draws the main menu of the game.
     */
    public void drawMainMenu() {
        if (usingKeyboard) {
            StdDraw.clear(Color.black);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
            StdDraw.text(width / 2, height - 5, "THE GAME");
            StdDraw.setPenColor(new Color(149, 185, 189));
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.text(width / 2, height - 13, "[C]hoose Name");
            StdDraw.text(width / 2, height - 16, "[P]ick Theme");
            StdDraw.text(width / 2, height - 19, "[N]ew Game");
            StdDraw.text(width / 2, height - 22, "[L]oad Game");
            StdDraw.text(width / 2, height - 25, "[Q]uit Game");
            StdDraw.show();
        }
    }

    /**
     * Draws the entering seed instructions for the user.
     */
    public void drawSeedInstructions() {
        if (usingKeyboard) {
            StdDraw.clear(Color.black);
            StdDraw.setPenColor(new Color(149, 185, 189));
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.text(width / 2, height / 2, "Enter random number then press 'S'");
            StdDraw.show();
        }
    }

    /**
     * Draws the entering name instructions for the user.
     */
    public void drawNameInstructions() {
        if (usingKeyboard) {
            StdDraw.clear(Color.black);
            StdDraw.setPenColor(new Color(149, 185, 189));
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.text(width / 2, height / 2, "Enter Avatar's Name Then Press '~'");
            StdDraw.show();
        }
    }

    /**
     * Draws the picking theme instructions for the user.
     */
    public void drawThemeInstructions() {
        if (usingKeyboard) {
            StdDraw.clear(Color.black);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
            StdDraw.text(width / 2, height - 5, "Press Theme Number");
            StdDraw.setPenColor(new Color(149, 185, 189));
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.text(width / 2, height - 10, "[1] Default");
            StdDraw.text(width / 2, height - 15, "[2] Castle");
            StdDraw.text(width / 2, height - 15, "[2] Castle");
            StdDraw.text(width / 2, height - 20, "[3] Dark Forest");
            StdDraw.text(width / 2, height - 25, "[4] Desert");
            StdDraw.show();
        }
    }

    /**
     * Sets the player name to the given string.
     * @param str       the given player name
     */
    public void setName(String str) {
        name = str;
    }

    /**
     * Returns the player's name.
     * @return          the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the game's theme to the given string.
     * @param str       the given theme
     */
    public void setTheme(String str) {
        theme = str;
    }

    /**
     * Returns the game's theme.
     * @return          the game's theme
     */
    public String getTheme() {
        return theme;
    }
}
