package grid;

import container.Container;
import crane.Crane;
import crane.Cranes;
import input.AlgorithmType;
import movement.Movement;
import slot.Slot;
import util.Util;

import java.util.*;

public class Grid {
    private Map<Integer, Slot> grid;
    private int maxHeight;
    private int targetHeight;
    private int currentHeight;
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
        this.currentHeight = maxHeight;
        this.length = length;
        this.width = width;
        type = AlgorithmType.TRANSFORMHEIGHT;
    }

    public boolean isAtHeight(int height){
        for(Slot s : grid.values()){
            if(s.getHeight() > height) return false;
        }
        return true;
    }

    public int getHeightTallestStack(){
        int currentHeight = 0;
        for(Slot s : grid.values()){
            if(s.getHeight() > currentHeight) currentHeight = s.getHeight();
        }
        return currentHeight;
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
        if(type == AlgorithmType.TRANSFORMTERMINAL && targetSlot.isAtHeight(maxHeight)){
            return false;
        }
        if(type == AlgorithmType.TRANSFORMHEIGHT && targetSlot.isHigherThan(currentHeight-1)){
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

    public boolean checkTargetSlotViableOutZone(Container containerToMove, Slot targetSlot){
        if(targetSlot.isHigherThan(maxHeight)){
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
                if(type == AlgorithmType.TRANSFORMTERMINAL){
                    if(!checkTargetSlotViable(containerToMove, slot)){
                        continue;
                    }
                }
                if(type == AlgorithmType.TRANSFORMHEIGHT){
                    if(!checkTargetSlotViableOutZone(containerToMove, slot)){
                        continue;
                    }
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

    public Slot findViableSlot(Container containerToMove, Cranes cranes){
        Slot currentSlot = getSlot(containerToMove.getSlotId());
        int[] interval = cranes.findPickupCraneInterval(currentSlot, containerToMove);
        //first check to see if there is a target slot without crossing over shared zone
            for(Slot slot : grid.values()){
                double xPickup = Util.calcContainerPickupX(containerToMove.getLength(), slot.getX());
                if(interval[0] <= xPickup && xPickup <= interval[1]){
                    if(checkTargetSlotViable(containerToMove, slot)){
                        return slot;
                    }
                }
            }
        // if it doesnt exist ==> search for slot across shared zone
            for(Slot slot : grid.values()){
                double xPickup = Util.calcContainerPickupX(containerToMove.getLength(), slot.getX());
                if(0 <= xPickup && xPickup <= length-1){
                    if(checkTargetSlotViable(containerToMove, slot)){
                        return slot;
                    }
                }
            }

        return null;
    }

    public boolean isFinished() {
        for(Slot s : grid.values()){
            if(s.getHeight() > targetHeight) return false;
        }
        return true;
    }

    public List<List<Container>> getWrongContainers() {
        List<Integer> wrongContainerIds = new ArrayList<>();
        List<List<Container>> wrongContainers = new ArrayList<>();
        for(Slot s : grid.values()){
            if(s.getHeight() == maxHeight && !wrongContainerIds.contains(s.getTopContainer().getId())){
                wrongContainerIds.add(s.getTopContainer().getId());
                wrongContainers.add(Arrays.asList(s.getTopContainer()));
            }
        }
        return wrongContainers;
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

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void updateCurrentHeight() {
        this.currentHeight--;
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
