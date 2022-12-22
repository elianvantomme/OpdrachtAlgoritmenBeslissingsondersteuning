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

    public static void moveOutZone(Grid grid, Cranes cranes){

    }

    public static void moveSingleWrongContainer(Container containerToMove, Grid grid, Cranes cranes) {
        /*
        STAP 0: kijken als target slot mogelijk is!
         */
        if (!grid.checkTargetSlotViable(containerToMove, grid.getSlot(containerToMove.getTargetSlotId()))) {
            return; // als je de container daar niet kan zetten
        }

        /*
        STAP 1: kijken voor usable kraan
         */
        Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
        Slot targetSlot = grid.getSlot(containerToMove.getTargetSlotId());

        List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot, containerToMove);
        if (!idealCranes.isEmpty()) {
            moveInZone(grid, cranes, targetSlot, initialSlot, containerToMove, idealCranes.get(0));
        } else {
            List<Crane> nonIdealCranes = cranes.getNonIdealCranes(initialSlot, targetSlot, containerToMove);
            Crane pickupCrane = nonIdealCranes.get(0);
            Crane dropOffCrane = nonIdealCranes.get(1);

            Movement.setGlobalTime(Math.max(pickupCrane.getLocalTime(), dropOffCrane.getLocalTime()));

            Slot viableSlot = grid.findViableSlot(cranes.getOverlapInterval(), containerToMove, pickupCrane, dropOffCrane);

            moveOfContainerWithCrane(initialSlot, viableSlot, pickupCrane, containerToMove, grid);

            //TODO check of het pad vrij is
            Movement emptyMovement = new Movement(dropOffCrane, pickupCrane, viableSlot);
            movements.addEmptyMovement(emptyMovement);

            moveOfContainerWithCrane(viableSlot, targetSlot, dropOffCrane, containerToMove, grid);
            //move with ideal crane van
        }


        /*
        STAP 2: Move kraan naar de locatie
         */

    }

    public static void moveStackOfContainers(Container containerToMove, Grid grid, Cranes cranes, List<List<Container>> wrongContainers) {

    }

    public static void moveContainer(Grid grid, Cranes cranes, Container containerToMove) {
        /*
        Aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
         */
        moveSingleWrongContainer(containerToMove, grid, cranes);

        /*
        if (wrongContainers.size() == 1) {
            moveSingleWrongContainer(containerToMove, grid, cranes);
        }
        Else is not used because there are no instances where a container (which is already at its target place) needs
        to be moved to make way to a container below to be placed elsewhere.

        else {
            moveStackOfContainers(containers, grid ,cranes, wrongContainers);
            moveSingleWrongContainer(containerToMove, grid ,cranes, wrongContainers);
        }
        */

    }


    public static boolean isFinished(Containers containers) {
        return containers.isFinished();
    }

    public static void main(String[] args) throws IOException, ParseException {
        // File initialYardFile = new File("src/main/instances/instances1/3t/TerminalA_20_10_3_2_160.json");
        // File targetYardFile = new File("src/main/instances/instances1/3t/targetTerminalA_20_10_3_2_160.json");
//        File initialYardFile = new File("src/main/instances/terminal22_1_100_1_10.json");
//        File targetYardFile = new File("src/main/instances/terminal22_1_100_1_10target.json");
//        File initialYardFile = new File("src/main/instances/6t/Terminal_10_10_3_1_100.json");
//        File targetYardFile = new File("src/main/instances/6t/targetTerminal_10_10_3_1_100.json");
        File initialYardFile = new File("src/main/instances/instances1/5t/TerminalB_20_10_3_2_160.json");
        File targetYardFile = new File("src/main/instances/instances1/5t/targetTerminalB_20_10_3_2_160.json");

        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();
        Containers containers = instance.getContainers();
        Grid grid = instance.getGrid();
        Cranes cranes = instance.getCranes();

        List<List<Container>> wrongContainers;
        List<Container> containersInCrane1 = new ArrayList<>();
        List<Container> containersInCrane2 = new ArrayList<>();
        List<Container> containersBetweenCranes = new ArrayList<>();
        List<Crane> usableCranes = cranes.getCraneMap().values().stream().toList();

        while (!isFinished(containers)) {
            wrongContainers = containers.getWrongContainers(grid);

            for (List<Container> stackWrongContainer : wrongContainers) {
                /*
                TODO checken voor welke kraan
                Kijken of het volledige pad binnen de zone van 1 kraan blijft zo ja voegen we deze container ook toe
                aan de sublijst van de kraan. Een kraan voert alles uit tot het moment dat er een movement van beide
                nodig is.
                */
                Container containerToMove = stackWrongContainer.get(0);
                Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
                Slot targetSlot = grid.getSlot(containerToMove.getTargetSlotId());

                List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot, containerToMove);
                if(!idealCranes.isEmpty()) {
                    Crane idealCrane = idealCranes.get(0);
                    if (idealCrane.equals(usableCranes.get(0))) {
                        containersInCrane1.add(containerToMove);
                    } else {
                        containersInCrane2.add(containerToMove);
                    }
                }
                else {
                    containersBetweenCranes.add(containerToMove);
                    break;
                }
//                System.out.println("press enter to continue");
//                sc.nextLine();
//                System.out.println(grid);
//                moveContainer(containers, grid, cranes, stackWrongContainer);
//                grid.update(movements.getLastMovement());
            }
            Iterator<Container> iteratorCont1 = containersInCrane1.iterator();
            while(iteratorCont1.hasNext()){
                Container container = iteratorCont1.next();
                moveContainer(grid,cranes,container);
                iteratorCont1.remove();
            }

            Iterator<Container> iteratorCont2 = containersInCrane2.iterator();
            while(iteratorCont2.hasNext()){
                Container container = iteratorCont2.next();
                moveContainer(grid,cranes,container);
                iteratorCont2.remove();
            }

            Iterator<Container> iteratorBetween = containersBetweenCranes.iterator();
            while(iteratorBetween.hasNext()){
                Container container = iteratorBetween.next();
                moveContainer(grid,cranes,container);
                iteratorBetween.remove();
            }
        }

        System.out.println("final movements");
        movements.print();
        movements.printToFile(targetYardFile.getPath());
    }
}
