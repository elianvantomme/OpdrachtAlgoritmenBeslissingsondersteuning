package container;

public class Container {
    private int id;
    private int length;
    private int slotId;

    public Container(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
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
