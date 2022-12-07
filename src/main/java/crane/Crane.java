package crane;

public class Crane {

    private int id;
    private double x;
    private double y;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int xSpeed;
    private int ySpeed;
    private boolean holdingContainer;

    public Crane(int id, double x, double y, int xMin, int xMax, int yMin, int yMax, int xSpeed, int ySpeed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isHoldingContainer() {
        return holdingContainer;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Crane{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", xMin=" + xMin +
                ", xMax=" + xMax +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                ", xSpeed=" + xSpeed +
                ", ySpeed=" + ySpeed +
                ", holdingContainer=" + holdingContainer +
                '}';
    }
}
