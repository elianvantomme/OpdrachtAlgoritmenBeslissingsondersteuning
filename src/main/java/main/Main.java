package main;

import container.Container;
import crane.Crane;
import input.InputReader;
import org.json.simple.parser.ParseException;
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
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("src/main/instances/terminal_4_3.json");
        InputReader inputReader = new InputReader(file);
        Yard yard = inputReader.getYard();
        Map<Integer, Slot> initialGrid = yard.getGrid();

        List<Crane> cranes = new ArrayList<>();
        Crane crane1 = new Crane(0,0,false);
        Crane crane2 = new Crane(0,3,false);
        cranes.add(crane1);
        cranes.add(crane2);
    }
}
