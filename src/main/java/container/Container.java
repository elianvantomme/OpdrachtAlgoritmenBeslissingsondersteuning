package container;

import java.util.List;

public class Container {
    private int id;
    private int length;
    private int slotId;
    private int targetSlotId;

    public Container(int id, int length) {
        this.id = id;
        this.length = length;
        this.targetSlotId = -1;
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

    //    public void addSlotId(int slotId) {
//        slotIds.add(slotId);
//    }
//
//    public List<Integer> getSlotIds() {
//        return slotIds;
//    }

    public boolean isInCorrectSlot(){
        return slotId == targetSlotId;
    }

    public int getId() {
        return id;
    }

    public void setTargetSlotId(int targetSlotId) {
        this.targetSlotId = targetSlotId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getTargetSlotId() {
        return targetSlotId;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", length=" + length +
                ", slotId=" + slotId +
                ", targetSlotId=" + targetSlotId +
                '}';
    }

}
