package crane;

import container.Container;
import point.Slot;

public class Movement {
    private Slot p1;
    private Slot p2;
    private double xSpeed;
    private double ySpeed;
    private double startTime;
    private double travelTime;
    private double endTime;
    static private double globalTime = 0;

    public Movement(Slot p1, Slot p2){
        this.p1 = p1;
        this.p2 = p2;
        this.startTime = globalTime;
        calculateTravelTime();
        globalTime = endTime;

    }

    private void calculateTravelTime(){
        double deltaX = Math.abs(p1.getX()-p2.getX());
        double deltaY = Math.abs(p1.getY() - p2.getY());
        travelTime = Math.max(deltaX, deltaY);
        endTime = startTime + travelTime;
    }
}
