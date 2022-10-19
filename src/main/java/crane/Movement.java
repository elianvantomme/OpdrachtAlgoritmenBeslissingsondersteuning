package crane;

import container.Container;
import point.Point;

public class Movement {
    private Point p1;
    private Point p2;
    private double xSpeed;
    private double ySpeed;
    private double startTime;
    private double travelTime;
    private double endTime;

    public Movement(Point p1, Point p2, double startTime){
        this.p1 = p1;
        this.p2 = p2;
        this.startTime = startTime;
        calculateTravelTime();
    }

    private void calculateTravelTime(){
        double deltaX = Math.abs(p1.getX()-p2.getX());
        double deltaY = Math.abs(p1.getY() - p2.getY());
        xSpeed = deltaX;
        ySpeed = deltaY;
        travelTime = (Math.sqrt(Math.pow(deltaY*Container.getWidth(),2)+ Math.pow(deltaX*Container.getLength(),2)))/(Math.sqrt(Math.pow(xSpeed,2))+Math.pow(ySpeed,2));
        endTime = startTime + travelTime;
    }
}
