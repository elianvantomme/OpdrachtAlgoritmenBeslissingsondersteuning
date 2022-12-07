package park;

import container.Container;
import crane.Crane;
import point.Slot;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Yard {

    private Map<Integer, Slot> grid;
    private Map<Integer, Crane> craneMap;
    private Map<Integer, Container> containerMap;

    public Yard(Map<Integer, Slot> grid, Map<Integer,Crane> craneMap, Map<Integer, Container> containerMap) {
        this.grid = grid;
        this.craneMap = craneMap;
        this.containerMap = containerMap;
        System.out.println("craneMap = " + craneMap);
    }

    public Map<Integer, Slot> getGrid() {
        return grid;
    }

    public Map<Integer, Crane> getCraneMap() {
        return craneMap;
    }

    public Map<Integer, Container> getContainerMap() {
        return containerMap;
    }
}
