package testjes;

import java.util.Objects;

class Key {
    private final int x;
    private final int y;
    private int hashCode;

    public Key(int x, int y) {
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hash(x+y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Key that = (Key) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}

public class hashTest {
    public static void main(String[] args) {
        Key coordinate1 = new Key(0,1);
        Key coordinate2 = new Key(1, 0);
        Key coordinate3 = new Key(1,2);

        System.out.println(Objects.hash(coordinate1) == Objects.hash(coordinate3));

    }
}
