package park;

import container.Container;
import crane.Movement;
import slot.Slot;

import java.util.Map;

public class Grid {
    private Map<Integer, Slot> grid;

    public Grid(Map<Integer, Slot> grid){
        this.grid = grid;
    }

    public void putContainerOnSlot(Container container, int slotId){
        grid.get(slotId).addContainer(container);
    }

    public Slot getSlot(int slotId){
        return grid.get(slotId);
    }

    public void update(Movement movement){
        Slot initialSlot = movement.getInitialSlot();
        Slot targetSlot = movement.getTargetSlot();
        Container movedContainer = initialSlot.popContainer();
        movedContainer.update(targetSlot.getId());
        targetSlot.putContainer(movedContainer);
    }

    @Override
    public String toString() {
        String gridString = "";
        for(Slot s : grid.values()){
            gridString += s.toString() + "\n";
        }
        return gridString;
    }
}
