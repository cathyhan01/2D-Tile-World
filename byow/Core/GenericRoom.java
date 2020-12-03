package byow.Core;

import byow.TileEngine.TETile;

/**
 * A generic object to model a room like structure.
 * @author Cathy Han
 */
public class GenericRoom {

    private int width, height;
    private TETile wallTileType, floorTileType;
    private Position lowerLeftPos;

    public GenericRoom(int w, int h, TETile wall, TETile floor, Position lowerLeft) {
        width = w;
        height = h;
        wallTileType = wall;
        floorTileType = floor;
        lowerLeftPos = lowerLeft;
    }

    /**
     * Get the width of the room.
     * @return  room width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the room.
     * @return  room height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the tile type of the room's walls.
     * @return  wall tile type
     */
    public TETile getWallTileType() {
        return wallTileType;
    }

    /**
     * Get the tile type of the room's floors.
     * @return  floor tile type
     */
    public TETile getFloorTileType() {
        return floorTileType;
    }

    /**
     * Get the upper right position of the room.
     * @return  upper right position
     */
    public Position getUpperRightPos() {
        return new Position(lowerLeftPos.getDeltaX(width), lowerLeftPos.getDeltaY(height));
    }

    /**
     * Get the lower left position of the room.
     * @return  lower left position
     */
    public Position getLowerLeftPos() {
        return lowerLeftPos;
    }
}
