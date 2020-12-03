package byow.Core;

/**
 * Represents a single x-coordinate position.
 * @author Cathy Han
 */
public class Position {

    private int x, y;

    public Position(int xCoor, int yCoor) {
        x = xCoor;
        y = yCoor;
    }

    /**
     * Gets the x coordinate position.
     * @return  x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate position.
     * @return  y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the x coordinate position with a specified delta value.
     * @return  x coordinate with delta
     */
    public int getDeltaX(int delta) {
        return x + delta;
    }

    /**
     * Gets the y coordinate position with a specified delta value.
     * @return  y coordinate with delta
     */
    public int getDeltaY(int delta) {
        return y + delta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Position other = (Position) o;
        return (this.getX() == other.getX() && this.getY() == other.getY());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
