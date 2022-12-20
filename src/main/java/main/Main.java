package main;

import container.Container;
import container.Containers;
import crane.Crane;
import crane.Cranes;
import crane.Movement;
import crane.Movements;
import input.InputReader;
import input.Instance;
import org.json.simple.parser.ParseException;
import park.Grid;
import slot.Slot;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Movements movements = new Movements();

    public static void moveWithIdealCrane(Slot initialSlot, Slot targetSlot, Crane crane, Container containerToMove, Grid grid) {
        Movement movement = new Movement(initialSlot, targetSlot, crane, containerToMove);
        movements.addContainerMovement(movement, grid);
    }

    public static void moveSingleWrongContainer(Container containerToMove, Grid grid ,Cranes cranes){
        /*
        STAP 0: een gepast target slot zoeken
         */
        if(!grid.checkTargetSlotViable(containerToMove, grid.getSlot(containerToMove.getTargetSlotId()))){
            return; // als je de container daar niet kan zetten
        }

        /*
        STAP 1: kijken voor usable kraan
         */
        Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
        Slot targetSlot = grid.getSlot(containerToMove.getTargetSlotId());

        List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot,containerToMove);
        if(!idealCranes.isEmpty()){
            Crane idealCrane = idealCranes.get(0);
            Crane blockingCrane = cranes.isPathFree(targetSlot, idealCrane);
            if(blockingCrane == null){
                moveWithIdealCrane(initialSlot, targetSlot, idealCrane, containerToMove, grid);
            }
            else{
                Movement movement = new Movement(idealCrane,blockingCrane, targetSlot);
                //EMPTY MOVEMENT
                movements.addEmptyMovement(movement);
                moveWithIdealCrane(initialSlot, targetSlot, idealCrane, containerToMove, grid);
            }
        }
        else{
            List<Crane> nonIdealCranes = cranes.getNonIdealCranes(initialSlot, targetSlot, containerToMove);
            Crane pickupCrane = nonIdealCranes.get(0);
            Crane dropOffCrane = nonIdealCranes.get(1);
            Slot viableSlot = grid.findViableSlot(cranes.getOverlapInterval(), containerToMove, pickupCrane, dropOffCrane);
            Movement placeTempMovement = new Movement(initialSlot, viableSlot,pickupCrane,containerToMove);
            movements.addContainerMovement(placeTempMovement,grid);

            //TODO check of het pad vrij is
            Movement emptyMovement = new Movement(dropOffCrane, pickupCrane, viableSlot);
            movements.addEmptyMovement(emptyMovement);

            Movement pickTempMovement = new Movement(viableSlot, targetSlot, dropOffCrane, containerToMove);
            movements.addContainerMovement(pickTempMovement, grid);
            //move with ideal crane van
        }
    }

    public static void moveStackOfContainers(Container containerToMove, Grid grid ,Cranes cranes, List<List<Container>> wrongContainers){

    }

//    public static void moveContainer(Containers containers, Grid grid ,Cranes cranes, List<Container> wrongContainers){
//        Container containerToMove = wrongContainers.get(0);
//        // aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
//        if(wrongContainers.size()==1){
//            moveSingleWrongContainer(containerToMove, grid ,cranes);
//        }else{
////            moveStackOfContainers(containers, grid ,cranes, wrongContainers);
////            moveSingleWrongContainer(containerToMove, grid ,cranes, wrongContainers);
//        }
//    }

    private static void moveContainer(Container wrongContainer, Grid grid, Cranes cranes) {
        moveSingleWrongContainer(wrongContainer, grid, cranes);
    }


    public static boolean isFinished(Containers containers){
        return containers.isFinished();
    }

    public static void main(String[] args) throws IOException, ParseException {

        File initialYardFile = new File("src/main/instances/instances1/2mh/MH2Terminal_20_10_3_2_100.json");

        InputReader inputReader = new InputReader(initialYardFile);
        Instance instance = inputReader.getInstance();
        Containers containers = instance.getContainers();
        Grid grid = instance.getGrid();
        Cranes cranes = instance.getCranes();

        List<Container> wrongContainers;
        while(!isFinished(containers)){
            wrongContainers = grid.getWrongContainers();
            for(Container wrongContainer : wrongContainers){
//                System.out.println("press enter to continue");
//                sc.nextLine();
//                System.out.println(grid);
                moveContainer(wrongContainer, grid ,cranes);
//                grid.update(movements.getLastMovement());
            }
        }
        System.out.println("final movements");
        movements.print();
    }

}
