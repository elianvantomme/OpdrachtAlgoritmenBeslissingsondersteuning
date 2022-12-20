package crane;

import container.Container;
import slot.Slot;
import util.Util;

import java.util.*;

public class Cranes {
    private Map<Integer, Crane> craneMap;
    private int[] overlapInterval;

    public Cranes(Map<Integer, Crane> craneMap) {
        this.craneMap = craneMap;
        overlapInterval = new int[2];
        overlapInterval[0] = -1;
        overlapInterval[1] = -1;
        calculateOverlapInterval();
    }

    private void calculateOverlapInterval() {
        if (craneMap.size() == 1) return;
        List<Crane> craneList = craneMap.values().stream().toList();
        Crane crane1 = craneList.get(0);
        Crane crane2 = craneList.get(1);
        int xMin = Math.max(crane1.getxMin(), crane2.getxMin());
        int xMax = Math.min(crane1.getxMax(), crane2.getxMax());
        if (xMax > xMin) {
            overlapInterval[0] = xMin;
            overlapInterval[1] = xMax;
        }
    }

    public Map<Integer, Crane> getCraneMap() {
        return craneMap;
    }

    public List<Crane> findIdealCranes(Slot currentSlot, Slot targetSlot, Container container) {
        List<Crane> idealCranes = new ArrayList<>();
        for (Crane crane : craneMap.values()) {
            double pickupX = Util.calcContainerPickupX(container.getLength(), currentSlot.getX());
            double dropOffX = Util.calcContainerPickupX(container.getLength(), targetSlot.getX());
            if (crane.checkIdealCrane(pickupX, dropOffX)) {
                idealCranes.add(crane);
            }
        }
        return idealCranes;
    }

    public List<Crane> getNonIdealCranes(Slot currentSlot, Slot targetSlot, Container container) {
        List<Crane> nonIdealCranes = new ArrayList<>();
        Crane pickupCrane = null;
        Crane dropOffCrane = null;
        double pickupX = Util.calcContainerPickupX(container.getLength(), currentSlot.getX());
        double dropOffX = Util.calcContainerPickupX(container.getLength(), targetSlot.getX());
        for (Crane crane : craneMap.values()) {
            if (crane.checkCraneCanPickUp(pickupX)) pickupCrane = crane;
            if (crane.checkCraneCanDropOff(dropOffX)) dropOffCrane = crane;
        }
        nonIdealCranes.add(pickupCrane);
        nonIdealCranes.add(dropOffCrane);
        return nonIdealCranes;
    }

    public Crane isPathFree(Slot targetSlot, Crane idealCrane) {
        boolean isPathFree = false;
        for (Crane crane : craneMap.values()) {
            //Move buiten het gebied
            if (!(crane.getId() == idealCrane.getId())) {
                if (crane.isInTheWay(targetSlot, idealCrane, crane)) {
                    return crane;
                }
            }
        }
        return null;
    }

    public int[] getOverlapInterval() {
        return overlapInterval;
    }

    @Override
    public String toString() {
        return "Cranes{" +
                "craneMap=" + craneMap +
                ", overlapInterval=" + Arrays.toString(overlapInterval) +
                '}';
    }
}

