package container;

import park.Grid;
import slot.Slot;

import java.util.*;

public class Containers {
    private Map<Integer, Container> containerMap;

    public Containers(Map<Integer, Container> containerMap) {
        this.containerMap = containerMap;
    }

    public void setTargetSlotId(int containerId, int slotId){
        containerMap.get(containerId).setTargetSlotId(slotId);
    }

    private boolean checkContainerCorrectSpot(Container container){
        return container.getSlotId() == container.getTargetSlotId();
    }

    private List<Container> getContainersOnTop(Slot slot, Container container){
        Stack<Container> containerStack = slot.getContainerStack();
        List<Container> containersOnTop = new ArrayList<>();
        System.out.println("container waar mogelijk containers op staan = " + container);
        for (int i = containerStack.size()-1; i >= 0 ; i--) {
            Container potentialOnTop = containerStack.get(i);
            System.out.println("potentialOnTop = " + potentialOnTop);
            if(potentialOnTop.getId() == container.getId()){
                break;
            }
            else {
                containersOnTop.add(0, potentialOnTop);
            }
        }
        return containersOnTop;
    }

    public List<List<Container>> getWrongContainers(Grid grid){
        List<List<Container>> wrongContainers = new ArrayList<>();
        for(Container container : containerMap.values()){
            if(!checkContainerCorrectSpot(container)){
                Slot slot = grid.getSlot(container.getSlotId());
                List<Container> containersToBeMoved = new ArrayList<>(Arrays.asList(container));
                containersToBeMoved.addAll(getContainersOnTop(slot, container));
                wrongContainers.add(containersToBeMoved);
            }
        }
        Collections.sort(wrongContainers, Comparator.comparingInt(List::size)); // zo kunnen we eerst de containers moven waar er geen op hun kop staan
        for(List<Container> containers : wrongContainers){
            System.out.println("containers = " + containers);
        }
        return wrongContainers;
    }

    @Override
    public String toString() {
        String containersString = "";
        for(Container c : containerMap.values()){
            containersString += c.toString() + "\n";
        }
        return containersString;
    }
}



