package slot;

import container.Container;

import java.util.Stack;

public class Slot {
    private int testing;
    private int id;
    private int x;
    private int y;
    private static int maxHeight;
    private Stack<Container> containerStack;

    public Slot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        containerStack = new Stack<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void addContainer(Container container){
        containerStack.add(container);
    }

    public Stack<Container> getContainerStack() {
        return containerStack;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", containerStack=" + containerStack +
                '}';
    }
}
