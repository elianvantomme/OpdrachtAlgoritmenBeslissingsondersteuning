package container;

import java.util.List;

public class Container {
    private int id;
    private int length;
    private int slotId;

    public Container(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public void update(int slotId) {
        this.slotId = slotId;
    }

    public int getLength() {
        return length;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getId() {
        return id;
    }


    public int getSlotId() {
        return slotId;
    }


    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", length=" + length +
                ", slotId=" + slotId +
                '}';
    }

}
