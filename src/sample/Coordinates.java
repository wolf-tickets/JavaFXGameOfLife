package sample;
import java.util.Objects;

public class Coordinates implements Comparable<Coordinates> {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash ( x, y );
    }

    @Override
    public int compareTo(Coordinates c) {

        if (this.y > c.y)
            return 1;
        else if (this.y < c.y)
            return -1;
        else if (this.x > c.x)
            return 1;
        else if (this.x < c.x)
            return -1;
        else return 0;

    }
}
