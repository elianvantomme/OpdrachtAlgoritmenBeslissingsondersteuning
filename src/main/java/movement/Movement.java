package movement;

import container.Container;
import crane.Crane;
import slot.Slot;
import util.Util;

public class Movement {
    /*
    x = xslot + lengte/2
    y = yslot + 1/2
    */
    private Container container;
    private int containerId;
    private Slot initialSlot;
    private Slot targetSlot;
    private double xInitial;
    private double xTarget;
    private double yInitial;
    private double yTarget;
    private Crane crane;
    //TODO wegen met de speed
//    private double xSpeed;
//    private double ySpeed;
    private double startTime;
    private double travelTimeWithContainer;
    private double endTime;
    private double travelTimeEmpty;
    static private double globalTime = 0;

    public Movement(Slot initialSlot, Slot targetSlot, Crane crane, Container container){
        this.initialSlot = initialSlot;
        this.targetSlot = targetSlot;

        this.xInitial = Util.calcContainerPickupX(container.getLength(), initialSlot.getX());
        this.yInitial = initialSlot.getY() + 0.5;

        this.xTarget = Util.calcContainerPickupX(container.getLength(), targetSlot.getX());
        this.yTarget = targetSlot.getY() + 0.5;

        this.startTime = globalTime;
        this.crane = crane;
        this.container = container;
        this.containerId = container.getId();
        calculateTravelTimeOfMoveContainer();
        crane.updateCrane(xTarget, yTarget);
        globalTime = endTime;
    }

    public Movement(Crane idealCrane, Crane blockingCrane, Slot targetSlot) {
        this.targetSlot = targetSlot;
        this.crane = blockingCrane;
        this.xInitial = blockingCrane.getX();
        this.yInitial = blockingCrane.getY();
        this.yTarget = blockingCrane.getY();
        this.startTime = globalTime;
        this.containerId = -1;
        if (idealCrane.getX() < blockingCrane.getX()){
            this.xTarget = blockingCrane.getX() + 2;
        }else if(idealCrane.getX() > blockingCrane.getX()){
            this.xTarget = blockingCrane.getX() - 2;
        }else{
            throw new IllegalArgumentException();
        }
        blockingCrane.updateCrane(xTarget, yTarget);
        calculateTravelTimeOfSafetyCrane();
        globalTime = endTime;
    }

    private void calculateTravelTimeOfMoveContainer(){
        double deltaX = Math.abs(xInitial- crane.getX());
        double deltaY = Math.abs(yInitial - crane.getY());
        travelTimeEmpty = Math.max(deltaX, deltaY);
        startTime = globalTime + travelTimeEmpty;

        double deltaX2 = Math.abs(xTarget- xInitial);
        double deltaY2 = Math.abs(yTarget - yInitial);
        travelTimeWithContainer = Math.max(deltaX2, deltaY2);
        endTime = startTime + travelTimeWithContainer;
    }

    private void calculateTravelTimeOfSafetyCrane(){
        double deltaX2 = Math.abs(xTarget- xInitial);
        double deltaY2 = Math.abs(yTarget - yInitial);
        travelTimeWithContainer = Math.max(deltaX2, deltaY2);
        endTime = startTime + travelTimeWithContainer;
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
                ","+containerId+
                ","+startTime+
                ","+endTime+
                ","+xInitial+
                ","+yInitial+
                ","+xTarget+
                ","+yTarget;
    }
}
