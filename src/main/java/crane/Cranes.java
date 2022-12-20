package crane;

import container.Container;
import slot.Slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cranes {
    private Map<Integer, Crane> craneMap = new HashMap<>();

    public Cranes(Map<Integer, Crane> craneMap) {
        this.craneMap = craneMap;
    }

    public Map<Integer, Crane> getCraneMap() {
        return craneMap;
    }

    @Override
    public String toString() {
        return "Cranes{" +
                "craneMap=" + craneMap +
                '}';
    }

    

    public List<Crane> findIdealCranes(Slot currentSlot, Slot targetSlot) {
        //TODO check voor de tussenzone
        List<Crane> idealCranes = new ArrayList<>();
        for (Crane crane : craneMap.values()) {
            if(crane.checkIdealCrane(currentSlot.getX(), targetSlot.getX())){
                idealCranes.add(crane);
            }
        }
        return idealCranes;
    }

    public List<Crane> getNonIdealCranes(Slot currentSlot, Slot targetSlot) {
        List<Crane> nonIdealCranes = new ArrayList<>();
        Crane pickupCrane = null;
        Crane dropOffCrane = null;
        for (Crane crane : craneMap.values()) {
            if (crane.checkCraneCanPickUp(currentSlot.getX())) pickupCrane = crane;
            if (crane.checkCraneCanDropOff(targetSlot.getY())) dropOffCrane = crane;
        }
        nonIdealCranes.add(pickupCrane);
        nonIdealCranes.add(dropOffCrane);
        return nonIdealCranes;
    }

    public Crane isPathFree(Slot targetSlot, Slot initialSlot, Crane idealCrane, Container containerToMove) {
        boolean isPathFree = false;
        for (Crane crane: craneMap.values()) {
            //Move buiten het gebied
            if(!crane.equals(idealCrane)){
                if(crane.isInTheWay(targetSlot, idealCrane, crane)){
                    return crane;
                };
            }
        }
        return null;
    }
}
