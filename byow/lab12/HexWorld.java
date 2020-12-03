package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    static Map<Integer, Integer> colToHex = Map.of(0, 3, 1, 4, 2, 5, 3, 4, 4, 3);

    public static void addHexagon(int s, TETile[][] world) {
        int xDraw = s - 1; // x-coordinate
        int yDraw = 2 * s; // y-coordinate

        System.out.println(colToHex.get(0));
//        for (int i = 0; i < colToHex.get(0); i++) {
//            Hexagon h = new Hexagon(s, new Position(xDraw, yDraw));
//            drawSingleHex(h, world);
//            System.out.println("In for loop");
//            yDraw += h.getHexHeight();
//        }


        for (int i = 0; i < 5; i++) {
            int numHexToDraw = colToHex.get(i);
            for (int j = 0; j < numHexToDraw; j++) {
                Hexagon h = new Hexagon(s, new Position(xDraw, yDraw));
                drawSingleHex(h, world);
                yDraw += h.getHexHeight();
            }
            xDraw += 2 * s - 1;
            yDraw -= s;
            break;
        }


//        drawSingleHex(new Hexagon(s, new Position(s-1, 0)), world);
    }

    private static void drawSingleHex(Hexagon hex, TETile[][] world) {
        boolean bottomHalf = true;

        Position p = hex.getPos();
        int yStart = p.getY();
        int yEnd = yStart + hex.getHexHeight();
        int xStart = p.getX();
        int xEnd = hex.getSide() + 1;

        System.out.println("In drawsinglehex");
        for (int y = yStart; y < yEnd; y++) { // y is row
            System.out.println("In outer for loop");
            for (int x = xStart; x < 3 * hex.getSide() - xEnd; x++) { // x is col
                world[x][y] = Tileset.TREE;
                System.out.println("world:  " +  "x: " + x + "  y: " + y);
            }

            if (y == yStart + hex.getSide() - 1) {
                bottomHalf = false;
                System.out.println("Change to top half");
                continue;
            }
            if (bottomHalf) {
                xStart--;
                xEnd--;
            } else {
                xStart++;
                xEnd++;
            }
        }
    }

    private static void fillWithNothing(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.DEFAULT_N;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        fillWithNothing(hexWorld);

        addHexagon(3, hexWorld);

        ter.renderFrame(hexWorld);
    }
}
