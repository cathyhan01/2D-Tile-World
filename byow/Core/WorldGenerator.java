package byow.Core;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import byow.TileEngine.Tileset;
import byow.TileEngine.TETile;

/*
A WorldGenerator object creates a 2D world of tiles. The world contains
rectangular rooms and both horizontal and vertical hallways.
@author Cathy Han, Annabelle Raven
 */
public class WorldGenerator {

    private static final int MAX_ROOM_WIDTH = 6;
    private static final int MAX_ROOM_HEIGHT = 6;
    private static final int MAX_NUM_ROOMS = 23;
    private static final int MIN_NUM_ROOMS = 5;
    private static final int NUM_MINES = 5;
    private static final int NUM_GOLD = 16;
    private long seed;
    private int width;
    private int height;
    private Random randSeed;
    private int numRooms;
    private List<Room> rooms;
    private List<Position> walkableTiles;
    private List<Position> floorTiles;
    private List<Position> mineTiles;
    private List<Position> goldTiles;
    private Position avatarPos;
    private TETile[][] world;
    private UIElements uiE;
    private Tileset ts;
    private String theme;
    private TETile nothing, wall, floor, mine, coin, egg, avatar;

    public WorldGenerator(long s, int w, int h, UIElements uie, String t) {
        seed = s;
        width = w;
        height = h;
        uiE = uie;
        theme = t;
        ts = new Tileset();
        randSeed = new Random(seed);
        numRooms = generateNumRooms();
        rooms = new ArrayList<>();
        walkableTiles = new ArrayList<>();
        floorTiles = new ArrayList<>();
        mineTiles = new ArrayList<>();
        goldTiles = new ArrayList<>();
        world = new TETile[width][height];
        setTiles();
        setAllNothing();
    }

    /**
     * Private helper method to set the wall, floor, etc. tiles to the
     * appropriate ones given the set theme.
     */
    private void setTiles() {
        for (TETile tile : ts.getTilesList()) {
            if (tile.theme().equals(theme) && tile.itemType().equals("NOTHING")) {
                nothing = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("WALL")) {
                wall = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("FLOOR")) {
                floor = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("MINE")) {
                mine = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("COIN")) {
                coin = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("EGG")) {
                egg = tile;
            } else if (tile.theme().equals(theme) && tile.itemType().equals("AVATAR")) {
                avatar = tile;
            }
        }
    }

    /**
     * Generates a pseudo-random number based on the seed fed during construction,
     * between 0(inclusive) and n(exclusive).
     * @param n         upper bound of range
     * @return          pseudo-random integer
     */
    private int generateRandomNum(int n) {
        return randSeed.nextInt(n);
    }

    /**
     * Pseudo-randomly generates the number of rooms in the world.
     * @return          the number of rooms
     */
    public int generateNumRooms() {
        int x = generateRandomNum(MAX_NUM_ROOMS);
        while (x < MIN_NUM_ROOMS) {
            x = generateRandomNum(MAX_NUM_ROOMS);
        }
        return x;
    }

    /**
     * Sets all the tiles in the 2D world to nothing by default
     * in order ot avoid null tiles in the world.
     */
    public void setAllNothing() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                world[x][y] = nothing;
            }
        }
    }

    /**
     * Creates the 2D World by populating the list of rooms in the world and then
     * draws those rooms. Connects the rooms by drawing hallways between the rooms.
     * @param isLoading     whether we're loading a previous game or not
     * @return              the fully generated 2D tile world
     */
    public TETile[][] createWorld(boolean isLoading) {
        setAllNothing();
        for (int i = 0; i < numRooms; i++) {
            Room r = createRandomRoom();
            while (overlaps(r)) {
                r = createRandomRoom();
            }
            rooms.add(r);
        }
        drawAllRooms();
        drawHallways();
        getTiles(walkableTiles);
        getTiles(floorTiles);
        placeMines();
        placeGold();
        Position randFloorPos = floorTiles.get(generateRandomNum(floorTiles.size()));
        int initialIndex = walkableTiles.indexOf(randFloorPos);
        placeAvatar(initialIndex, isLoading);
        return getWorld();
    }

    /**
     * Getter method for returning the current state of the world.
     * @return          the 2D tile world
     */
    public TETile[][] getWorld() {
        return world;
    }

    /**
     * Private helper method for populating a certain list of tiles in the world.
     * Walkable tiles include floor tiles(which can later become mine or gold tiles).
     * @param lst       the given list to add to
     */
    private void getTiles(List<Position> lst) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (world[x][y].equals(floor)) {
                    lst.add(new Position(x, y));
                }
            }
        }
    }

    /**
     * Places the avatar at a certain position(accessed through the given index).
     * Then draws the avatar at the specific position.
     * @param i             the given index
     * @param isLoading     whether we're loading a previous game or not
     */
    private void placeAvatar(int i, boolean isLoading) {
        avatarPos = walkableTiles.get(i);
        int x = avatarPos.getX();
        int y = avatarPos.getY();
        boolean isFloor = world[x][y].equals(floor);
        boolean isMine = world[x][y].equals(mine);
        boolean isGold = world[x][y].equals(coin) || world[x][y].equals(egg);
        if (isFloor) {
            floorTiles.remove(floorTiles.indexOf(avatarPos));
        } else if (isMine) {
            mineTiles.remove(mineTiles.indexOf(avatarPos));
            uiE.useMine(isLoading);
        } else if (isGold) {
            goldTiles.remove(goldTiles.indexOf(avatarPos));
            uiE.incrementScore(world[x][y].equals(egg), isLoading);
        }
        world[x][y] = avatar;
        if (uiE.getUsingKeyboard()) {
            world[x][y].draw(x, y);
        }
    }

    /**
     * Determines what new position to move the avatar to depending on the given input character.
     * @param c             the given character
     * @param isLoading     whether we're loading a previous game or not
     */
    public void moveAvatar(char c, boolean isLoading) {
        int currX = avatarPos.getX();
        int currY = avatarPos.getY();
        uiE.setUserMoved(true);
        Position nextStep = null;
        if (c == 'w') {
            nextStep = new Position(currX, currY + 1);
        } else if (c == 'a') {
            nextStep = new Position(currX - 1, currY);
        } else if (c == 's') {
            nextStep = new Position(currX, currY - 1);
        } else if (c == 'd') {
            nextStep = new Position(currX + 1, currY);
        }
        if (nextStep != null && walkableTiles.contains(nextStep)) {
            Position prevAvatarPos = avatarPos;
            placeAvatar(walkableTiles.indexOf(nextStep), isLoading);
            floorTiles.add(prevAvatarPos);
            world[prevAvatarPos.getX()][prevAvatarPos.getY()] = floor;
        }
    }

    /**
     * Places mines at available random floor tile locations.
     */
    private void placeMines() {
        for (int i = 0; i < NUM_MINES; i++) {
            int index = generateRandomNum(floorTiles.size());
            Position p = floorTiles.remove(index);
            world[p.getX()][p.getY()] = mine;
            mineTiles.add(p);
        }
    }

    /**
     * Places gold(coins or an egg) at available random floor tile locations.
     */
    private void placeGold() {
        for (int i = 0; i < NUM_GOLD; i++) {
            TETile tile;
            if (i == 0) {
                tile = egg;
            } else {
                tile = coin;
            }
            int index = generateRandomNum(floorTiles.size());
            Position p = floorTiles.remove(index);
            world[p.getX()][p.getY()] = tile;
            goldTiles.add(p);
        }
    }

    /**
     * Draws hallways in the world between rooms to connect them.
     * @source      RogueSharp V3 Tutorial – Simple Room Generation
     * roguesharp.wordpress.com/2016/04/03/roguesharp-v3-tutorial-connecting-rooms-with-hallways/
     */
    public void drawHallways() {
        for (int i = 1; i < rooms.size(); i++) {
            Room prev = rooms.get(i - 1);
            Room curr = rooms.get(i);
            int prevRoomX = prev.getLowerLeftPos().getX() + prev.getWidth() / 2;
            int prevRoomY = prev.getLowerLeftPos().getY() + prev.getHeight() / 2;
            int currRoomX = curr.getLowerLeftPos().getX() + curr.getWidth() / 2;
            int currRoomY = curr.getLowerLeftPos().getY() + curr.getHeight() / 2;
            if (generateRandomNum(100) % 2 == 0) {
                drawHorizontalHall(prevRoomX, currRoomX, prevRoomY);
                drawVerticalHall(prevRoomY, currRoomY, currRoomX);
            } else {
                drawVerticalHall(prevRoomY, currRoomY, prevRoomX);
                drawHorizontalHall(prevRoomX, currRoomX, currRoomY);
            }
        }
    }

    /**
     * Draws a horizontal hallway within the given coordinate range.
     * @param x1        given x-coordinate
     * @param x2        other given x-coordinate
     * @param y         given y-coordinate
     * @source          RogueSharp V3 Tutorial – Simple Room Generation
     * roguesharp.wordpress.com/2016/04/03/roguesharp-v3-tutorial-connecting-rooms-with-hallways/
     */
    public void drawHorizontalHall(int x1, int x2, int y) {
        int xStart = Math.min(x1, x2);
        int xEnd = Math.max(x1, x2);
        for (int x = xStart; x <= xEnd; x++) {
            world[x][y] = floor;
            drawSingleWall(x, y - 1);
            drawSingleWall(x, y + 1);
            if (x == xStart) {
                drawSingleWall(x - 1, y - 1);
                drawSingleWall(x - 1, y + 1);
            }
            if (x == xEnd) {
                drawSingleWall(x + 1, y - 1);
                drawSingleWall(x + 1, y + 1);
            }
        }
    }

    /**
     * Draws a vertical hallway within the given coordinate range.
     * @param y1        given y-coordinate
     * @param y2        other given y-coordinate
     * @param x         given x-coordinate
     * @source          RogueSharp V3 Tutorial – Simple Room Generation
     * roguesharp.wordpress.com/2016/04/03/roguesharp-v3-tutorial-connecting-rooms-with-hallways/
     */
    public void drawVerticalHall(int y1, int y2, int x) {
        int yStart = Math.min(y1, y2);
        int yEnd = Math.max(y1, y2);
        for (int y = yStart; y <= yEnd; y++) {
            world[x][y] = floor;
            drawSingleWall(x - 1, y);
            drawSingleWall(x + 1, y);
            if (y == yStart) {
                drawSingleWall(x - 1, y - 1);
                drawSingleWall(x + 1, y - 1);
            }
        }
    }

    /**
     * Draws a single wall tile in the world at the given coordinates
     * @param x         the given x-coordinate
     * @param y         the given y-coordinate
     */
    private void drawSingleWall(int x, int y) {
        if (!world[x][y].equals(wall) && !world[x][y].equals(floor)) {
            world[x][y] = wall;
        }
    }

    /**
     * Creates a Room object with random dimensions at a random location.
     * @return          the generated Room object
     */
    public Room createRandomRoom() {
        int w = generateRandomNum(MAX_ROOM_WIDTH) + 4;
        int h = generateRandomNum(MAX_ROOM_HEIGHT) + 4;
        int x = generateRandomNum(width - w - 1);
        int y = generateRandomNum(height - h - 1);
        Position p = new Position(x, y);
        return new Room(w, h, wall, floor, p);
    }

    /**
     * Draws all the rooms in the world.
     */
    public void drawAllRooms() {
        for (Room r : rooms) {
            drawSingleRoom(r);
        }
    }

    /**
     * Draws a single given room.
     * @param rm        the GenericRoom object to draw
     */
    public void drawSingleRoom(Room rm) {
        Position lowerLeft = rm.getLowerLeftPos();
        Position upperRight = rm.getUpperRightPos();
        for (int y = lowerLeft.getY(); y < upperRight.getY(); y++) {
            for (int x = lowerLeft.getX(); x < upperRight.getX(); x++) {
                if (x == lowerLeft.getX() || y == lowerLeft.getY()) {
                    world[x][y] = rm.getWallTileType();
                } else if (x == upperRight.getX() - 1 || y == upperRight.getY() - 1) {
                    world[x][y] = rm.getWallTileType();
                } else {
                    world[x][y] = rm.getFloorTileType();
                }
            }
        }
    }

    /**
     * Determines if the given room overlaps with ANY of the existing Rooms in the world.
     * Overlap is defined as having a border coordinate that is either equal to the border
     * coordinate of another room OR having a corner inside that room. Sharing a wall is
     * considered overlapping. Thick walls(ie. double walls) are not considered overlap.
     * @param room      the given room
     * @return          true if overlap exists, false otherwise
     * @source          https://www.baeldung.com/java-check-if-two-rectangles-overlap
     */
    public boolean overlaps(Room room) {
        if (rooms.size() <= 1) {
            return false;
        }
        Position thisLf = room.getLowerLeftPos();
        Position thisRt = room.getUpperRightPos();
        for (Room r : rooms) {
            if (r.equals(room)) {
                continue;
            }
            Position otherLf = r.getLowerLeftPos();
            Position otherRt = r.getUpperRightPos();
            boolean onSide = (thisLf.getX() > otherRt.getX() || thisRt.getX() < otherLf.getX());
            boolean aboveBelow = (thisLf.getY() > otherRt.getY() || thisRt.getY() < otherLf.getY());
            if (!onSide && !aboveBelow) {
                return true;
            }
        }
        return false;
    }

}
