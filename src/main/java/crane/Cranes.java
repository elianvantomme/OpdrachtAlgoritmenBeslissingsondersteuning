package crane;

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
}
