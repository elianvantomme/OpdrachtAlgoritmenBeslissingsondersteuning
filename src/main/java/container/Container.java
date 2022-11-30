package container;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Container {
    private int id;
    private int length;
    private List<Integer> slotIds;

    public Container(int id, int length) {
        this.id = id;
        this.length = length;
        slotIds = new ArrayList<>();
    }

    public int getLength() {
        return length;
    }

    public void addSlotId(int slotId) {
        slotIds.add(slotId);
    }

    public List<Integer> getSlotIds() {
        return slotIds;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", length=" + length +
                ", slotIds=" + slotIds +
                '}';
    }
}
