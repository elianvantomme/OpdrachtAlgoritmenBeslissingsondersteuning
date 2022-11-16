package crane;

public class Crane {

    private int x;
    private int y;
    private boolean holdingContainer;

    public Crane(int x, int y, boolean holdingContainer){
        this.x = x;
        this.y = y;
        this.holdingContainer = holdingContainer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHoldingContainer() {
        return holdingContainer;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
