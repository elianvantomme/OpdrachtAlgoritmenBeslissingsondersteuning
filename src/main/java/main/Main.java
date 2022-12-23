package main;

import container.Container;
import container.Containers;
import crane.Crane;
import crane.Cranes;
import input.AlgorithmType;
import movement.Movement;
import movement.Movements;
import input.InputReader;
import input.Instance;
import org.json.simple.parser.ParseException;
import grid.Grid;
import slot.Slot;
import visualisation.GridVisualizer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Movements movements = new Movements();
    static AlgorithmType type;

    public static void moveOfContainerWithCrane(Slot initialSlot, Slot targetSlot, Crane crane, Container containerToMove, Grid grid) {
        Movement movement = new Movement(initialSlot, targetSlot, crane, containerToMove);
        movements.addContainerMovement(movement, grid);
    }

    public static void moveInZone(Grid grid, Cranes cranes, Slot targetSlot, Slot initialSlot, Container containerToMove, Crane idealCrane) {
        Crane blockingCrane = cranes.isPathFree(targetSlot, idealCrane);
        if (blockingCrane == null){
            moveOfContainerWithCrane(initialSlot, targetSlot, idealCrane, containerToMove,grid);
        } else {
            //EMPTY MOVEMENT
            Movement movement = new Movement(idealCrane, blockingCrane, targetSlot);
            movements.addEmptyMovement(movement);

            moveOfContainerWithCrane(initialSlot, targetSlot, idealCrane, containerToMove,grid);
        }
    }

    public static void moveOutZone(Grid grid, Cranes cranes, Slot targetSlot, Slot initialSlot, Container containerToMove){
        List<Crane> nonIdealCranes = cranes.getNonIdealCranes(initialSlot, targetSlot, containerToMove);
        Crane pickupCrane = nonIdealCranes.get(0);
        Crane dropOffCrane = nonIdealCranes.get(1);
        Slot tempSlot = null;
        if(type == AlgorithmType.TRANSFORMTERMINAL){
            tempSlot = grid.findViableSlot(cranes.getOverlapInterval(), containerToMove, pickupCrane, dropOffCrane);
        }
        if(type == AlgorithmType.TRANSFORMHEIGHT){
            // hier mag hij even geplaatst worden tot hoogte maxHeight
            tempSlot = grid.findViableSlot(cranes.getOverlapInterval(), containerToMove, pickupCrane, dropOffCrane);
        }
        moveOfContainerWithCrane(initialSlot,tempSlot,pickupCrane,containerToMove,grid);
//        Movement placeTempMovement = new Movement(initialSlot, tempSlot,pickupCrane,containerToMove);
//        movements.addContainerMovement(placeTempMovement,grid);

            Movement emptyMovement = new Movement(dropOffCrane, pickupCrane, tempSlot);
            movements.addEmptyMovement(emptyMovement);
        //Empty movement
        Movement emptyMovement = new Movement(dropOffCrane, pickupCrane, tempSlot);
        movements.addEmptyMovement(emptyMovement);

        moveOfContainerWithCrane(tempSlot,targetSlot,dropOffCrane,containerToMove,grid);
    }

    }
    public static void moveSingleWrongContainer(Container containerToMove, Grid grid ,Cranes cranes){
        /*
        STAP 0: kijken als target slot mogelijk is!
         */
        Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
        Slot targetSlot = null;
        if(type == AlgorithmType.TRANSFORMTERMINAL){
            targetSlot = grid.getSlot(containerToMove.getTargetSlotId());
        }
        if(type == AlgorithmType.TRANSFORMHEIGHT){
            targetSlot = grid.findViableSlot(containerToMove, cranes);
        }
        if(!grid.checkTargetSlotViable(containerToMove, targetSlot)){
            return; // als je de container daar niet kan zetten
        }

        /*
        STAP 1: kijken voor usable kraan
         */

        List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot,containerToMove);
        if(!idealCranes.isEmpty()){
            moveInZone(grid, cranes, targetSlot, initialSlot, containerToMove, idealCranes.get(0));
        }
        else{
            moveOutZone(grid,cranes,targetSlot,initialSlot,containerToMove);
        }
    }

    public static void moveContainer(Containers containers, Grid grid ,Cranes cranes, List<Container> wrongContainers){
        Container containerToMove = wrongContainers.get(0);
        // aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
        if(wrongContainers.size()==1){
            moveSingleWrongContainer(containerToMove, grid ,cranes);
        }else{
            //TODO this is never necesary in the instances, so not implemented
//            moveStackOfContainers(containers, grid ,cranes, wrongContainers);
//            moveSingleWrongContainer(containerToMove, grid ,cranes, wrongContainers);
        }
    }


    public static boolean isFinished(Containers containers){
        return containers.isFinished();
    }

    public static boolean isFinished(Grid grid){
        return grid.isFinished();
    }

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        InputReader inputReader = null;
        File initialYardFile = new File(args[0]);
        if(args.length == 1){
             inputReader = new InputReader(initialYardFile);
        }
        if(args.length == 2){
            File targetYardFile = new File(args[1]);
             inputReader = new InputReader(initialYardFile, targetYardFile);
        }

        Instance instance = inputReader.getInstance();
        Containers containers = instance.getContainers();
        Grid grid = instance.getGrid();
        Cranes cranes = instance.getCranes();
        type = instance.getType();
        System.out.println("algorithm type = " + type);

        GridVisualizer gridVisualizer = new GridVisualizer(grid);

        if(type == AlgorithmType.TRANSFORMTERMINAL){
            List<List<Container>> wrongContainers;
            while(!isFinished(containers)){
                wrongContainers = containers.getWrongContainers(grid);
                for(List<Container> stackWrongContainer : wrongContainers){
                    moveContainer(containers, grid ,cranes, stackWrongContainer);
                    gridVisualizer.update();
                }
            }
        }

        if(type == AlgorithmType.TRANSFORMHEIGHT){
            List<List<Container>> wrongContainers;
            while(!isFinished(grid)){
                System.out.println(grid);
                wrongContainers = grid.getWrongContainers();
                for(List<Container> container : wrongContainers){
                    Thread.sleep(500);
                    moveContainer(containers, grid, cranes, container);
                    gridVisualizer.update();
                }
                if(grid.getHeightTallestStack() == grid.getCurrentHeight()-1){
                    grid.updateCurrentHeight();
                }
            }
        }

        System.out.println("final movements");
        movements.print();
    }
}
