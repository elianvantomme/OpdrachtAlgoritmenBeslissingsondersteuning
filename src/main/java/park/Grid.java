package park;

import container.Container;
import crane.Movement;
import slot.Slot;

import java.util.Map;

public class Grid {
    private Map<Integer, Slot> grid;
    private int maxHeight;
    private int length;
    private int width;
    private int[][] landScape;

    public Grid(Map<Integer, Slot> grid, int maxHeight, int length, int width) {
        this.grid = grid;
        this.maxHeight = maxHeight;
        this.length = length;
        this.width = width;
        landScape = new int[length][width];
//        generateLandScape();
    }

    public void generateLandScape() {
        for (Slot s : grid.values()) {
            landScape[s.getX()][s.getY()] = s.getHeight();
        }
    }

    public void putContainerOnSlot(Container container, int slotId) {
        grid.get(slotId).putContainer(container);
        generateLandScape();
    }

    public Slot getSlot(int slotId) {
        return grid.get(slotId);
    }

    public void update(Movement movement) {
        Slot initialSlot = movement.getInitialSlot();
        Slot targetSlot = movement.getTargetSlot();
        Container movedContainer = initialSlot.popContainer();
        movedContainer.update(targetSlot.getId());
        targetSlot.putContainer(movedContainer);
        generateLandScape();
    }

    private boolean isCertainlyNotStackable(Container containerToMove, Slot targetSlot) {
        /*
        when the values along the lenght of the container are not the same
        then it's certainly not possible to put the container there, else it could be possible
         */
        int[] row = landScape[targetSlot.getY()];
        int height = row[targetSlot.getX()];
        //TODO kleiner of gelijk aan weet ik niet zo goed
        for (int i = targetSlot.getX() + 1; i <= containerToMove.getLength(); i++) {
            if (row[i] != height) return true;
        }
        return false;
    }

    private boolean leftOk(Container containerToMove, Slot targetSlot, int height){
        Slot currentSlot = targetSlot;
        int currentSlotId = targetSlot.getId();
        int maxLength = 0;
        //TODO left zou oke moeten zijn heb gecontroleerd op papier
        //left side
        boolean leftOk = false;
        while (getSlot(currentSlotId).getX() > 0) {
            currentSlotId--;
            currentSlot = getSlot(currentSlotId);
            maxLength++;
            if (currentSlot.getHeight() == height) {
                Container container = currentSlot.getContainerAtHeight(height);
                if (container.getLength() == maxLength) {
                    leftOk = true;
                    break;
                }
            }
        }
        return leftOk;
    }

    private boolean rightOk(Container containerToMove, Slot targetSlot, int height){
        //right side
        //TODO zou ook juist moeten zijn gecontroleerd op papier
        boolean rightOk = true;
        int currentSlotId = targetSlot.getId();
        Slot currentSlot = getSlot(currentSlotId);
        int maxLength = containerToMove.getLength();
        for (int i = currentSlotId; i < currentSlotId+containerToMove.getLength(); i++) {
            currentSlot = getSlot(currentSlotId);
            if(currentSlot.getHeight() == height){
                Container container = currentSlot.getContainerAtHeight(height);
                if(container.getLength() > maxLength){
                    rightOk = false;
                    break;
                }else{
                    maxLength--;
                }
            }
        }
        return rightOk;
    }

    private boolean isStackable(Container containerToMove, Slot targetSlot) {
        int[] row = landScape[targetSlot.getY()];
        int height = row[targetSlot.getX()];
        return leftOk(containerToMove, targetSlot, height) && rightOk(containerToMove, targetSlot, height);
    }

    public boolean checkTargetSlotViable(Container containerToMove) {
        Slot targetSlot = getSlot(containerToMove.getTargetSlotId());
        //1 max height
        if (targetSlot.isMaxHeight(maxHeight)) {
            return false;
        }
        //2 stacking constraint
        //2.1 container kan enkel gestacked worden op een even ondergrond (allemaal dezelfde hoogte)
        if (isCertainlyNotStackable(containerToMove, targetSlot)) {
            return false;
        }
        //2.2 container moet voldoen aan stacking voorwaarden ==> uteinden lopen parallel
        if (!isStackable(containerToMove, targetSlot)) {
            return false;
        }
        //als aan alle voorwaarden voldaan --> true returnen
        return true;
    }

    public void printLandscape() {
        for (int r = 0; r < landScape.length; r++) {
            for (int c = 0; c < landScape[r].length; c++)
                System.out.print(landScape[r][c] + " ");
            System.out.println();
        }
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
