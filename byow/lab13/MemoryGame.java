package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String str = "";
        for (int i = 0; i < n; i++) {
            str += CHARACTERS[rand.nextInt(26)];
        }
        return str;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
        StdDraw.text(width / 2, height / 2, s);


        //TODO: If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.setFont(new Font("Arial", Font.BOLD, 15));
            StdDraw.textLeft(0, height - 1, "Round: " + round);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
            StdDraw.textRight(width, height - 1, ENCOURAGEMENT[rand.nextInt(7)]);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String str = "";
        while (str.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                str += c;
                drawFrame(str);
            }
        }
        return str;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;
        //TODO: Establish Engine loop
        while (!gameOver) {
            String expected = generateRandomString(round);
            flashSequence(expected);
            playerTurn = true;
            String userTyped = solicitNCharsInput(round);
            while (userTyped.length() < round) {
                userTyped = solicitNCharsInput(round);
            }
            if (userTyped.equals(expected)) {
                round++;
                playerTurn = false;
            } else {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
            }
        }
    }

}
