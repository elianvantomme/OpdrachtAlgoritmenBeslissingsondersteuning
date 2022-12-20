package input;

import container.Containers;
import crane.Cranes;
import grid.Grid;

public class Instance {
    private Grid grid;
    private Cranes cranes;
    private Containers containers;

    public Instance(Grid grid, Cranes cranes, Containers containers) {
        this.grid = grid;
        this.cranes = cranes;
        this.containers = containers;
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
                ", grid=" + grid +
                ", cranes=" + cranes +
                ", containers=" + containers +
                '}';
    }
}
