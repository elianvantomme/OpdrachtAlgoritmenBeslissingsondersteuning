package point;

import container.Container;

import java.util.List;
import java.util.Stack;

public class Slot {

    private int id;
    private int x;
    private int y;
    private static int maxHeight;
    private Stack<Container> containerList;

    public Slot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        containerList = new Stack<>();
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
        containerList.add(container);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", containerList=" + containerList +
                '}';
    }
}
