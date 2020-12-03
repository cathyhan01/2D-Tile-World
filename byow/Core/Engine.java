package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Engine {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int KEYBOARD = 1;
    private static final int STRING = 0;
    private UIElements uiHandler;
    private int inputOption;
    private long seed;
    private Input inputHandler;
    private boolean enteredMainMenu;
    private boolean finishedSeed;
    private boolean saveAndQuit;
    private WorldGenerator wg;
    private TETile[][] world;
    private TERenderer TER;
    private String gamePlayString;
    private boolean isEnteringName;
    private String playerName;
    private boolean isEnteringTheme;
    private String theme;

    public Engine() {
        TER = new TERenderer();
        enteredMainMenu = false;
        finishedSeed = false;
        saveAndQuit = false;
        isEnteringName = false;
        isEnteringTheme = false;
        gamePlayString = "";
        playerName = "Anonymous Player";
        theme = "default";
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        uiHandler = new UIElements(WIDTH, HEIGHT, true);
        inputOption = KEYBOARD;
        createHandler("");
        uiHandler.setUsingKeyboard(true);
        uiHandler.drawMainMenu();
        while (inputHandler.hasNextInput() && !saveAndQuit) {
            handleChars();
        }
        System.exit(1);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        uiHandler = new UIElements(WIDTH, HEIGHT, false);
        inputOption = STRING;
        createHandler(input);
        uiHandler.setUsingKeyboard(false);
        while (inputHandler.hasNextInput() && !saveAndQuit) {
            handleChars();
        }
        return world;
    }

    /**
     * Creates the appropriate input handler depending on the input option.
     * @param str       the input string(empty if keyboard input)
     * @source Hug - DemoInputSource.java
     */
    private void createHandler(String str) {
        if (inputOption == STRING) {
            inputHandler = new StringInput(str);
        } else if (inputOption == KEYBOARD) {
            inputHandler = new KeyboardInput(uiHandler);
        }
    }

    /**
     * Processes the input characters and determines what behavior the game should have.
     * @source Hug - DemoInputSource.java
     */
    private void handleChars() {
        char c = inputHandler.getNextChar();
        if (!enteredMainMenu) {
            if (isEnteringName && c != '~') {
                playerName += c;
            } else if (c == 'p' && !isEnteringName) {
                isEnteringTheme = true;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.drawThemeInstructions();
                }
            } else if (c == '1' && isEnteringTheme) {
                theme = "default"; isEnteringTheme = false;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.setTheme("default"); uiHandler.drawMainMenu();
                }
            } else if (c == '2' && isEnteringTheme) {
                theme = "castle"; isEnteringTheme = false;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.setTheme("castle"); uiHandler.drawMainMenu();
                }
            } else if (c == '3' && isEnteringTheme) {
                theme = "forest"; isEnteringTheme = false;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.setTheme("forest"); uiHandler.drawMainMenu();
                }
            } else if (c == '4' && isEnteringTheme) {
                theme = "desert"; isEnteringTheme = false;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.setTheme("desert"); uiHandler.drawMainMenu();
                }
            } else if (c == 'n' && !isEnteringName) {
                enteredMainMenu = true;
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.drawSeedInstructions();
                }
            } else if (c == 'c' && !isEnteringName) {
                isEnteringName = true; playerName = "";
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.drawNameInstructions();
                }
            } else if (c == '~' && isEnteringName) {
                if (uiHandler.getUsingKeyboard()) {
                    uiHandler.setName(playerName); uiHandler.drawMainMenu();
                }
                isEnteringName = false;
            } else if (c == 'l' && !isEnteringName) {
                loadGame(); enteredMainMenu = true; finishedSeed = true;
            } else if (c == 'q' && !isEnteringName) {
                saveAndQuit = true;
            }
        } else {
            if (!finishedSeed) {
                if (c >= 48 && c <= 57) {
                    seed = seed * 10 + Long.parseLong("" + c);
                } else if (c == 's') {
                    finishedSeed = true;
                    wg = new WorldGenerator(seed, WIDTH, HEIGHT, uiHandler, theme);
                    world = wg.createWorld(false);
                    if (inputHandler.getClass() == KeyboardInput.class) {
                        inputHandler.setPlaying(); inputHandler.setWorld(wg.getWorld());
                        TER.initialize(WIDTH, HEIGHT); drawWorld();
                    }
                }
            } else {
                if (c == 'q' && inputHandler.getPrevChar() == ':') {
                    saveAndQuit = true; saveGame();
                } else {
                    gamePlayString += c; wg.moveAvatar(c, false); world = wg.getWorld();
                    if (uiHandler.getUsingKeyboard()) {
                        drawWorld();
                    }
                }
            }
        }
    }

    /**
     * Private helper method for drawing the current world state.
     */
    private void drawWorld() {
        TER.renderFrame(world);
        uiHandler.drawGameStats();
    }

    /**
     * Private helper method to save the current game state.
     * @source https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
     */
    private void saveGame() {
        try {
            FileWriter writer = new FileWriter("MyFile.txt", false);
            writer.write(seed + "\n");
            writer.write(gamePlayString + "\n");
            writer.write(uiHandler.getName() + "\n");
            writer.write(uiHandler.getTheme());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private helper method to load the last previous saved game state.
     * @source https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
     */
    private void loadGame() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MyFile.txt"));
            seed = Long.parseLong(reader.readLine());
            gamePlayString = reader.readLine();
            playerName = reader.readLine();
            theme = reader.readLine();
            uiHandler.setTheme(theme);
            wg = new WorldGenerator(seed, WIDTH, HEIGHT, uiHandler, theme);
            world = wg.createWorld(true);
            for (int i = 0; i < gamePlayString.length(); i++) {
                char c = gamePlayString.charAt(i);
                wg.moveAvatar(c, true);
            }
            uiHandler.setName(playerName);
            if (uiHandler.getUsingKeyboard()) {
                inputHandler.setWorld(world);
                inputHandler.setPlaying();
                TER.initialize(WIDTH, HEIGHT);
                drawWorld();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
