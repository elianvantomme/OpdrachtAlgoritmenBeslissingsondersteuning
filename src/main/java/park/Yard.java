package park;

import crane.Crane;
import point.Slot;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Yard {

    private Map<Integer, Slot> grid;
    private List<Crane> craneList;

    public Yard(Map<Integer, Slot> grid) {
        this.grid = grid;
        craneList = new ArrayList<>();
    }

    public Map<Integer, Slot> getGrid() {
        return grid;
    }
}
