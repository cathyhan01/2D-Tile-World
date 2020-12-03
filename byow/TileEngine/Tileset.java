package byow.TileEngine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    private List<TETile> tiles = new ArrayList<>();

    public Tileset() {
        tiles.add(DEFAULT_AVATAR); tiles.add(DEFAULT_MINE); tiles.add(DEFAULT_COIN);
        tiles.add(DEFAULT_EGG); tiles.add(DEFAULT_W); tiles.add(DEFAULT_F); tiles.add(DEFAULT_N);

        tiles.add(FOREST_AVATAR); tiles.add(FOREST_MINE); tiles.add(FOREST_COIN);
        tiles.add(FOREST_EGG); tiles.add(GRASS); tiles.add(TREE); tiles.add(WATER);

        tiles.add(KNIGHT); tiles.add(CASTLE_MINE); tiles.add(CASTLE_COIN); tiles.add(CASTLE_EGG);
        tiles.add(CASTLE_WALL); tiles.add(CASTLE_FLOOR); tiles.add(CASTLE_NOTHING);

        tiles.add(DESERT_AVATAR); tiles.add(DESERT_MINE); tiles.add(DESERT_COIN);
        tiles.add(DESERT_EGG); tiles.add(SAND); tiles.add(DESERT_FLOOR); tiles.add(PYRAMID);

        tiles.add(FLOWER); tiles.add(LOCKED_DOOR); tiles.add(UNLOCKED_DOOR);
    }

    public List<TETile> getTilesList() {
        return tiles;
    }

    /* Unused tiles */
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink,
            "flower", "FLOWER", "default");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door", "DOOR", "default");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door", "DOOR", "default");



    /* DEFAULT */
    public static final TETile DEFAULT_AVATAR = new TETile('@', Color.black,
            new Color(222, 214, 184), "You", "AVATAR", "default");

    public static final TETile DEFAULT_W = new TETile('#', new Color(211, 219, 206), Color.GRAY,
            "wall", "WALL", "default");

    public static final TETile DEFAULT_F = new TETile('.', new Color(153, 133, 101),
            new Color(222, 214, 184), "floor", "FLOOR", "default");

    public static final TETile DEFAULT_N = new TETile('.', Color.darkGray, Color.black,
            "nothing", "NOTHING", "default");

    public static final TETile DEFAULT_MINE = new TETile('.', new Color(153, 133, 101),
            new Color(222, 214, 184), "mine", "MINE", "default");

    public static final TETile DEFAULT_COIN = new TETile('o', Color.yellow,
            new Color(222, 214, 184), "coin", "COIN", "default");

    public static final TETile DEFAULT_EGG = new TETile('o', Color.yellow,
            new Color(222, 214, 184), "egg", "EGG", "default");



    /* FOREST */
    public static final TETile FOREST_AVATAR = new TETile('@', Color.black,
            new Color(72, 117, 70), "You", "AVATAR", "forest");

    public static final TETile GRASS = new TETile('"', new Color(19, 115, 15),
            new Color(72, 117, 70), "grass",
            "byow/Core/images/proj3_grass.jpeg", "FLOOR", "forest");

    public static final TETile TREE = new TETile('♠', new Color(27, 112, 24),
            new Color(140, 105, 39), "tree", "byow/Core/images/proj3_tree.jpeg",
            "WALL", "forest");

    public static final TETile WATER = new TETile('★', Color.yellow, new Color(24, 44, 110),
            "water", "NOTHING", "forest");

    public static final TETile FOREST_MINE = new TETile('"', new Color(19, 115, 15),
            new Color(72, 117, 70), "mine", "byow/Core/images/proj3_grass.jpeg", "MINE", "forest");

    public static final TETile FOREST_COIN = new TETile('o', Color.yellow,
            new Color(72, 117, 70), "coin", "COIN", "forest");

    public static final TETile FOREST_EGG = new TETile('o', Color.yellow, new Color(72, 117, 70),
            "egg", "EGG", "forest");



    /* CASTLE */
    public static final TETile KNIGHT = new TETile('@', Color.black, Color.lightGray,
            "You", "byow/Core/images/proj3_knight.jpeg", "AVATAR", "castle");

    public static final TETile CASTLE_WALL = new TETile('♠', Color.black, Color.GRAY,
            "castle wall", "byow/Core/images/proj3_castle_wall.jpeg",
            "WALL", "castle");

    public static final TETile CASTLE_FLOOR = new TETile('.', Color.black, Color.lightGray,
            "castle floor", "byow/Core/images/proj3_cobblestone_floor.jpeg",
            "FLOOR", "castle");

    public static final TETile CASTLE_NOTHING = new TETile('∫',
            new Color(49, 112, 46), new Color(96, 133, 94), "grass",
            "byow/Core/images/proj3_grass.jpeg", "NOTHING", "castle");

    public static final TETile CASTLE_MINE = new TETile('.', Color.black, Color.lightGray,
            "mine", "byow/Core/images/proj3_cobblestone_floor.jpeg", "MINE", "castle");

    public static final TETile CASTLE_COIN = new TETile('o', Color.yellow, Color.lightGray,
            "coin", "COIN", "castle");

    public static final TETile CASTLE_EGG = new TETile('o', Color.yellow, Color.lightGray,
            "egg", "EGG", "castle");



    /* DESERT */
    public static final TETile DESERT_AVATAR = new TETile('@', Color.black,
            new Color(186, 155, 115), "You", "AVATAR", "desert");

    public static final TETile SAND = new TETile('≈', new Color(247, 233, 178),
            new Color(247, 217, 106), "sand", "NOTHING", "desert");

    public static final TETile PYRAMID = new TETile('▲', Color.yellow, new Color(222, 214, 184),
            "pyramid", "WALL", "desert");

    public static final TETile DESERT_FLOOR = new TETile('.', Color.black,
            new Color(186, 155, 115), "desert floor", "FLOOR", "desert");

    public static final TETile DESERT_MINE = new TETile('.', Color.black,
            new Color(186, 155, 115), "mine", "MINE", "desert");

    public static final TETile DESERT_COIN = new TETile('o', Color.yellow,
            new Color(186, 155, 115), "coin", "COIN", "desert");

    public static final TETile DESERT_EGG = new TETile('o', Color.yellow,
            new Color(186, 155, 115), "egg", "EGG", "desert");

}



