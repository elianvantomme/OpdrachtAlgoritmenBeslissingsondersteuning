package slot;

import container.Container;

import java.util.Stack;

public class Slot {
    private int id;
    private int x;
    private int y;
    private Stack<Container> containerStack;

    public Slot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        containerStack = new Stack<>();
    }

    public boolean isMaxHeight(int maxHeight) {
        return containerStack.size() == maxHeight;
    }

    public int getHeight(){
        return containerStack.size();
    }

    public Container getContainerAtHeight(int height){
        return containerStack.get(height-1);
    }

    public Container popContainer(){
        return containerStack.pop();
    }

    public void putContainer(Container container){
        containerStack.push(container);
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
