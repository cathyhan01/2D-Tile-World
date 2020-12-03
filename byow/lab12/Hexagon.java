package byow.lab12;

public class Hexagon {

    private final int side;
    private final Position p;

    public Hexagon(int s, Position pos) {
        side = s;
        p = pos;
    }

    public int getHexHeight() {
        return side * 2;
    }

    public int getHexWidth() {
        return side + 2 * (side - 1);
    }


    public Position getPos() {
        return p;
    }

    public int getSide() {
        return side;
    }
}