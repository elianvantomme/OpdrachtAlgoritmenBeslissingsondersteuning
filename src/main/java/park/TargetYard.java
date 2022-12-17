package park;

import java.util.Map;

public class TargetYard {
    private int maxHeight;
    private Map<Integer, Integer> assignments;

    public TargetYard(int maxHeight, Map<Integer, Integer> assignments) {
        this.maxHeight = maxHeight;
        this.assignments = assignments;
    }

    public Map<Integer, Integer> getAssignments(){
        return assignments;
    }
}
