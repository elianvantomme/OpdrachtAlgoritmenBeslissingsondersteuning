package input;

import container.Containers;
import crane.Cranes;
import grid.Grid;

public class Instance {
    private Grid grid;
    private Cranes cranes;
    private Containers containers;
    private AlgorithmType type;

    public Instance(Grid grid, Cranes cranes, Containers containers, AlgorithmType type) {
        this.grid = grid;
        this.cranes = cranes;
        this.containers = containers;
        this.type = type;
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

    public AlgorithmType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Instance{" +
                ", grid=" + grid +
                ", cranes=" + cranes +
                ", containers=" + containers +
                '}';
    }
}
