package byow.Core;

import byow.TileEngine.TETile;

/**
 * A room object, inherits from a GenericRoom object.
 * Has specified dimensions, location, and wall/floor tile types.
 * @author Cathy Han
 */
public class Room extends GenericRoom {

    public Room(int w, int h, TETile wall, TETile floor, Position pos) {
        super(w, h, wall, floor, pos);
    }
}
