package main;

import container.Container;
import crane.Crane;
import input.InputReader;
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
    public static void moveContainer(Container container, Slot destinationSlot, Yard yard, List<Crane> cranes){
        Crane usableCrane = null;
        if (!destinationSlot.getContainerList().contains(container)){
            for (Crane crane: cranes) {
                if(!crane.isHoldingContainer() && usableCrane == null){
                    usableCrane = crane;
                }
            }
        }
    }

    public static boolean isContainerCorrect(Instance instance, Container container, Slot slot){
        if(container.getSlotId() == )
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
        for (Map.Entry<Integer, Integer> entry : desiredGrid.entrySet()) {
            Slot slot = initialGrid
        }

        System.out.println(initialGrid.toString());
    }

    public static void main(String[] args) throws IOException, ParseException {
        File initialYardFile = new File("src/main/instances/terminal22_1_100_1_10.json");
        File targetYardFile = new File("src/main/instances/terminal22_1_100_1_10target.json");
        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();





    }
}
