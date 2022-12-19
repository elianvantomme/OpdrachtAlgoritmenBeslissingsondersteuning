package crane;

import java.util.HashMap;
import java.util.Map;

public class Cranes {
    private Map<Integer, Crane> craneMap = new HashMap<>();

    public Cranes(Map<Integer, Crane> craneMap) {
        this.craneMap = craneMap;
    }

    @Override
    public String toString() {
        return "Cranes{" +
                "craneMap=" + craneMap +
                '}';
    }
}
