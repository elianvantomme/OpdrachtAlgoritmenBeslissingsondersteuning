package main;

import container.Container;
import crane.Crane;
import input.InputReader;
import input.Instance;
import org.json.simple.parser.ParseException;
import park.TargetYard;
import park.Yard;
import slot.Slot;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void moveContainer(Container container, Slot initialSlot, Slot destinationSlot,List<Crane> cranes, Map<Integer, Slot> grid){
        /*
        Gaan checken ofdat het mogelijk is om hem daar te plaatsen
            -   Geen enkele op een dubbele
            -   Niet overschrijdven van de max height
            -   Kraan moet er kunnen raken
        */
    }

    public static void moveSingleWrongContainer(Container container, Instance instance){

    }

    public static void moveStackOfContainers(){

    }



    public static void moveContainer(Instance instance, List<List<Container>> wrongContainers){
        Container containerToMove = wrongContainers.get(0).get(0);
        // aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
        if(wrongContainers.get(0).size()==1){
            moveSingleWrongContainer();
        }else{
            moveStackOfContainers();
            moveSingleWrongContainer();
        }
    }

    //TODO dit misschien beter in de Slot klasse zetten
    public static boolean isContainerCorrect(Container container, Slot slot){
        return slot.getContainerStack().contains(container);
    }

    //TODO misschien dit in de instance reader zetten ?
    public static void makeDesiredYard(Instance instance) {
        Yard initialYard = instance.getInitialYard();
        TargetYard targetYard = instance.getDesiredYard();
        // key: slotId, value: slot
        Map<Integer, Slot> initialGrid = initialYard.getGrid();
        // key: containerId, value: slotId
        Map<Integer, Integer> desiredGrid = targetYard.getAssignments();
        Map<Integer, Container> containerMap = initialYard.getContainerMap();
        List<Container> correctContainersList = new ArrayList<>();
//        System.out.println(desiredGrid);
        for (Map.Entry<Integer, Integer> entry : desiredGrid.entrySet()) {
            Slot destinationSlot = initialGrid.get(entry.getValue());
            Container container = containerMap.get(entry.getKey());
            if (isContainerCorrect(container,destinationSlot)){
                correctContainersList.add(container);
            }else{
                int slotId = container.getSlotIds().get(0);
                Slot initialSlot = initialGrid.get(slotId);


//                System.out.println("Initial "+ initialSlot);
//                System.out.println("Destination "+ destinationSlot);
//                System.out.println();
            }
//            System.out.println(entry);
//            System.out.println(container);
//            System.out.println(slot);
//            System.out.println();
        }

//        System.out.println(initialGrid.toString());
    }

    public static boolean checkContainerCorrectSpot(Container container, int correctSlot){
        return container.getSlotIds().get(0) == correctSlot;
    }


    public static List<Container> getContainersOnTop(Slot slot, Container container){
        Stack<Container> containerStack = slot.getContainerStack();
        List<Container> containersOnTop = new ArrayList<>();
        for (int i = 0; i < containerStack.size(); i++) {
            Container potentialOnTop = containerStack.get(i);
            if(potentialOnTop.getId() == container.getId()){
                break;
            }
            else {
                containersOnTop.add(potentialOnTop);
            }
        }
        return containersOnTop;
    }


    public static List<List<Container>> getWrongContainers(Instance instance){
        Yard initialYard = instance.getInitialYard();
        TargetYard targetYard = instance.getDesiredYard();
        Map<Integer, Slot> initialGrid = initialYard.getGrid();
        Map<Integer, Integer> desiredGrid = targetYard.getAssignments();
        Map<Integer, Container> containerMap = initialYard.getContainerMap();

        List<List<Container>> wrongContainers = new ArrayList<>();

        for(Container container : containerMap.values()){
//            System.out.println("container = " + container + " ==> desired slot = " + desiredGrid.get(container.getId()));
            if(!checkContainerCorrectSpot(container, desiredGrid.get(container.getId()))){
                Slot slot = instance.getInitialYard().getGrid().get(container.getSlotIds().get(0));
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

    public static boolean isFinished(List<List<Container>> wrongContainers){
        return wrongContainers.isEmpty();
    }

    public static void main(String[] args) throws IOException, ParseException {
        File initialYardFile = new File("src/main/instances/instances1/3t/TerminalA_20_10_3_2_160.json");
        File targetYardFile = new File("src/main/instances/instances1/3t/targetTerminalA_20_10_3_2_160.json");
        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();
        makeDesiredYard(instance); //TODO dit in instance reader zetten?

        System.out.println("instance.getInitialYard().getGrid() = " + instance.getInitialYard().getGrid());
        List<List<Container>> wrongContainers = getWrongContainers(instance);
//        List<Container> wrongContainers = getWrongContainers(instance);
//        List<Container> containersOnTop = getContainersOnTop();
        while(!isFinished(wrongContainers)){
            System.out.println("press enter to continue");
            sc.nextLine();
            wrongContainers = getWrongContainers(instance);
            moveContainer(instance, wrongContainers);
        }


//        System.out.println(instance.getInitialYard());

    }
}
