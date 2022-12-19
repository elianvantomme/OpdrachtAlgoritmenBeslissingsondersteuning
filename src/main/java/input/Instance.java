package input;

import container.Container;
import container.Containers;
import crane.Crane;
import crane.Cranes;
import park.Grid;
import park.TargetYard;
import park.Yard;
import slot.Slot;

import java.util.Map;

public class Instance {
    private int maxHeight;
    private Grid grid;
    private Cranes cranes;
    private Containers containers;

    public Instance(int maxHeight, Grid grid, Cranes cranes, Containers containers) {
        this.maxHeight = maxHeight;
        this.grid = grid;
        this.cranes = cranes;
        this.containers = containers;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public Grid getGrid() {
        return grid;
    }

    public Cranes getCranes() {
        return cranes;
    }

    public Containers getContainers() {
        return containers;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "maxHeight=" + maxHeight +
                ", grid=" + grid +
                ", cranes=" + cranes +
                ", containers=" + containers +
                '}';
    }
}
