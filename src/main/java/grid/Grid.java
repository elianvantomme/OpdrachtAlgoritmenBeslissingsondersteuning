package grid;

import container.Container;
import crane.Crane;
import input.AlgorithmType;
import input.InputReader;
import movement.Movement;
import slot.Slot;
import util.Util;

import java.util.*;

public class Grid {
    private Map<Integer, Slot> grid;
    private int maxHeight;
    private int targetHeight;
    private int length;
    private int width;
    private AlgorithmType type;

    public Grid(Map<Integer, Slot> grid, int maxHeight, int length, int width) {
        this.grid = grid;
        this.maxHeight = maxHeight;
        this.length = length;
        this.width = width;
        type = AlgorithmType.TRANSFORMTERMINAL;
    }

    public Grid(Map<Integer, Slot> grid, int maxHeight, int targetHeight, int length, int width) {
        this.grid = grid;
        this.maxHeight = maxHeight;
        this.targetHeight = targetHeight;
        this.length = length;
        this.width = width;
        type = AlgorithmType.TRANSFORMHEIGHT;
    }

    public void putContainerOnSlot(Container container, int slotId) {
        for (int i = slotId; i <= slotId + container.getLength() - 1; i++) {
            grid.get(i).putContainer(container);
        }
    }

    public void removeContainerFromSlot(Container container, int slotId){
        for (int i = slotId; i <= slotId + container.getLength() - 1; i++) {
            grid.get(i).popContainer();
        }
    }

    public Slot getSlot(int slotId) {
        return grid.get(slotId);
    }

    public void update(Movement movement) {
        Slot initialSlot = movement.getInitialSlot();
        Slot targetSlot = movement.getTargetSlot();
        Container movedContainer = movement.getContainer();
        movedContainer.update(targetSlot.getId());
        removeContainerFromSlot(movedContainer, initialSlot.getId());
        putContainerOnSlot(movedContainer, targetSlot.getId());
    }

    public boolean isFlatSurface(Container containerToMove, Slot targetSlot) {
        int height = targetSlot.getHeight();
        for (int i = targetSlot.getId() + 1; i < targetSlot.getId() + containerToMove.getLength(); i++) {
            if (getSlot(i).getHeight() != height) return false;
        }
        return true;
    }

    public boolean isStackable(Container containerToMove, Slot targetSlot) {
        int height = targetSlot.getHeight();
        if (height > 0) {
            int lengthBelow = 0;
            List<Integer> usedContainers = new ArrayList<>();
            for (int i = targetSlot.getId(); i < targetSlot.getId() + containerToMove.getLength(); i++) {
                Container topContainer = getSlot(i).getTopContainer();
                if (!usedContainers.contains(topContainer.getId())) {
                    usedContainers.add(topContainer.getId());
                    lengthBelow += topContainer.getLength();
                }
            }
            return containerToMove.getLength() == lengthBelow;
        }
        return true;
    }

    public boolean checkTargetSlotViable(Container containerToMove, Slot targetSlot) {
        if (targetSlot.isMaxHeight(maxHeight)) {
            return false;
        }
        if (!isFlatSurface(containerToMove, targetSlot)) {
            return false;
        }
        if (!isStackable(containerToMove, targetSlot)) {
            return false;
        }
        return true;
    }

    public Slot findViableSlot(int[] interval, Container containerToMove, Crane pickupCrane, Crane dropOffCrane) {
        for(Slot slot : grid.values()){
            if(interval[0] <= slot.getX() && slot.getX() <= interval[1]){
                if(!checkTargetSlotViable(containerToMove, slot)){
                    continue;
                }
                if(!pickupCrane.checkCraneCanPickUp(Util.calcContainerPickupX(containerToMove.getLength(), slot.getX()))){
                    continue;
                }
                if(!dropOffCrane.checkCraneCanPickUp(Util.calcContainerPickupX(containerToMove.getLength(), slot.getX()))){
                    continue;
                }
                return slot;
            }
        }
        return null;
    }

    public double calcContainerPickupX(int containerLength, int slotX){
        return slotX + (double) containerLength/2;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public Map<Integer, Slot> getGrid() {
        return grid;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public String toString() {
        String gridString = "";
        for (Slot s : grid.values()) {
            gridString += s.toString() + "\n";
        }
        return gridString;
    }

}
