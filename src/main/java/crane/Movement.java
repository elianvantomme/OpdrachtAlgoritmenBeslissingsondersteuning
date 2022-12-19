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
    private double travelTimeWithContainer;
    private double endTime;
    private double travelTimeEmpty;
    static private double globalTime = 0;

    public Movement(Slot initialSlot, Slot targetSlot, Crane crane, Container container){
        this.initialSlot = initialSlot;
        this.targetSlot = targetSlot;
        this.xInitial = initialSlot.getX() + (double) container.getLength() / 2;
        this.yInitial = initialSlot.getY() + 0.5;
        this.xTarget = targetSlot.getX() + (double) container.getLength() / 2;
        this.yTarget = targetSlot.getY() + 0.5;
        this.startTime = globalTime;
        this.crane = crane;
        this.container = container;
        calculateTravelTime();
        crane.updateCrane(xTarget, yTarget);
        globalTime = endTime;
    }
    public Movement(Slot initialSlot, Slot targetSlot, Crane crane){
        this.initialSlot = initialSlot;
        this.targetSlot = targetSlot;
        this.xInitial = initialSlot.getX() + (double) container.getLength() / 2;
        this.yInitial = initialSlot.getY() + 0.5;
        this.xTarget = targetSlot.getX() + (double) container.getLength() / 2;
        this.yTarget = targetSlot.getY() + 0.5;
        this.startTime = globalTime;
        this.crane = crane;
        calculateTravelTime();
        crane.updateCrane(xTarget, yTarget);
        globalTime = endTime;
    }

    private void calculateTravelTime(){
        double deltaX = Math.abs(xInitial- crane.getX());
        double deltaY = Math.abs(yInitial - crane.getY());
        travelTimeEmpty = Math.max(deltaX, deltaY);
        startTime = globalTime + travelTimeEmpty;

        double deltaX2 = Math.abs(xTarget- xInitial);
        double deltaY2 = Math.abs(yTarget - yInitial);
        travelTimeWithContainer = Math.max(deltaX2, deltaY2);
        endTime = startTime + travelTimeWithContainer;
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
