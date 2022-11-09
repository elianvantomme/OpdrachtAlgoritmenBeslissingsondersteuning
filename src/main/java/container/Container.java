package container;

public class Container {
    private int id;
    private int length;

    public Container(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", length=" + length +
                '}';
    }
}
