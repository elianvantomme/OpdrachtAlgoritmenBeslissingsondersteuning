package main;

import container.Container;
import crane.Crane;
import crane.Movement;
import input.InputReader;
import input.Instance;
import org.json.simple.parser.ParseException;
import park.TargetYard;
import park.Yard;
import point.Slot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void moveContainer(Container container, Slot initialSlot, Slot destinationSlot,List<Crane> cranes, Map<Integer, Slot> grid){
        /*
        Gaan checken ofdat het mogelijk is om hem daar te plaatsen
            -   Geen enkele op een dubbele
            -   Niet overschrijdven van de max height
            -   Kraan moet er kunnen raken
        */
    }

    public static boolean isContainerCorrect(Container container, Slot slot){
        return slot.getContainerList().contains(container);
    }

    public static void makeDesiredYard(Instance instance) {
        Yard initialYard = instance.getInitialYard();
        TargetYard targetYard = instance.getDesiredYard();
        // key: slotId, value: slot
        Map<Integer, Slot> initialGrid = initialYard.getGrid();
        // key: containerId, value: slotId
        Map<Integer, Integer> desiredGrid = targetYard.getAssignments();
        Map<Integer, Container> containerMap = initialYard.getContainerMap();
        List<Container> correctContainersList = new ArrayList<>();
        System.out.println(desiredGrid);
        for (Map.Entry<Integer, Integer> entry : desiredGrid.entrySet()) {
            Slot destinationSlot = initialGrid.get(entry.getValue());
            Container container = containerMap.get(entry.getKey());
            if (isContainerCorrect(container,destinationSlot)){
                correctContainersList.add(container);
            }else{
                int slotId = container.getSlotIds().get(0);
                Slot initialSlot = initialGrid.get(slotId);


                System.out.println("Initial "+ initialSlot);
                System.out.println("Destination "+ destinationSlot);
                System.out.println();
            }
//            System.out.println(entry);
//            System.out.println(container);
//            System.out.println(slot);
//            System.out.println();
        }

//        System.out.println(initialGrid.toString());
    }

    public static void main(String[] args) throws IOException, ParseException {
        File initialYardFile = new File("src/main/instances/terminal22_1_100_1_10.json");
        File targetYardFile = new File("src/main/instances/terminal22_1_100_1_10target.json");
        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();
        makeDesiredYard(instance);

    }
}
