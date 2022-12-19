package crane;

import container.Container;
import slot.Slot;

public class Movement {
    /*
    x = xslot + lengte/2
    y = yslot + 1/2
    */
    private Container container;
    private Slot initialSlot;
    private Slot targetSlot;
    private double xInitial;
    private double xTarget;
    private double yInitial;
    private double yTarget;
    private Crane crane;
    //TODO wegen met de speed
    private double xSpeed;
    private double ySpeed;
    private double startTime;
    private double travelTime;
    private double endTime;
    static private double globalTime = 0;

    public Movement(Slot initialSlot, Slot targetSlot, Crane crane, Container container){
        this.initialSlot = initialSlot;
        this.targetSlot = targetSlot;
        this.xInitial = initialSlot.getX() + container.getLength();
        this.yInitial = initialSlot.getY() + 0.5;
        this.xTarget = targetSlot.getX();
        this.yTarget = targetSlot.getY();
        this.startTime = globalTime;
        this.crane = crane;
        this.container = container;
        calculateTravelTime();
        crane.updateCrane(xTarget, yTarget);
        globalTime = endTime;
    }

    private void calculateTravelTime(){
        double deltaX = Math.abs(xTarget- xInitial);
        double deltaY = Math.abs(yTarget - yInitial);
        travelTime = Math.max(deltaX, deltaY);
        endTime = startTime + travelTime;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public Container getContainer() {
        return container;
    }

    public Slot getInitialSlot() {
        return initialSlot;
    }

    public Slot getTargetSlot() {
        return targetSlot;
    }

    @Override
    public String toString() {
        return crane.getId()+
                ";"+container.getId()+
                ";"+startTime+
                ";"+endTime+
                ";"+xInitial+
                ";"+yInitial+
                ";"+xTarget+
                ";"+yTarget;
    }
}
