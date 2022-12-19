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

//    public static void moveContainer(Container container, Slot initialSlot, Slot destinationSlot,List<Crane> cranes, Map<Integer, Slot> grid){
//        /*
//        Gaan checken ofdat het mogelijk is om hem daar te plaatsen
//            -   Geen enkele op een dubbele
//            -   Niet overschrijdven van de max height
//            -   Kraan moet er kunnen raken
//        */
//    }


    public static void moveWithIdealContainer(Slot initialSlot, Slot targetSlot, Crane crane, Container containerToMove){
        Movement movement = new Movement(initialSlot, targetSlot, crane, containerToMove);
        System.out.println("\n"+movement +"\n");
        movements.addMovement(movement);
    }

    public static void moveSingleWrongContainer(Container containerToMove, Grid grid ,Cranes cranes){
        /*
        STAP 0: kijken als target slot mogelijk is!
         */
        if(grid.checkTargetSlotViable(containerToMove)){

        }


        /*
        STAP 1: kijken voor usable kraan
         */

        Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
        Slot targetSlot = grid.getSlot(containerToMove.getTargetSlotId());

        List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot);
        if(!idealCranes.isEmpty()){
            Crane idealCrane = idealCranes.get(0);

            cranes.isPathFree(targetSlot, initialSlot, idealCrane, containerToMove);
            if( == null){
                moveWithIdealContainer(initialSlot, targetSlot, idealCrane, containerToMove);
            }
            else{
                Movement movement = new Movement(idealCrane,)
            }
        }
        else{
            List<Crane> nonIdealCranes = cranes.getNonIdealCranes(initialSlot, targetSlot);
            Crane pickupCrane = nonIdealCranes.get(0);
            Crane dropOffCrane = nonIdealCranes.get(1);

            //move with ideal crane van
        }


        /*
        STAP 1.5: Is het pad vrij
         */


        /*
        STAP 2: Move kraan naar de locatie
         */



        /*
        STAP 3: Checken of de destination vrij is en kan geplaatst worden:
            - Top down gaan plaatsen
            - Max Height niet overschreden
            - Even ondergrond
         */


    }

    public static void moveStackOfContainers(Container containerToMove, Grid grid ,Cranes cranes, List<List<Container>> wrongContainers){

    }

    public static void moveContainer(Containers containers, Grid grid ,Cranes cranes, List<List<Container>> wrongContainers){
        Container containerToMove = wrongContainers.get(0).get(0);
        // aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
        if(wrongContainers.get(0).size()==1){
            moveSingleWrongContainer(containerToMove, grid ,cranes);
        }else{
//            moveStackOfContainers(containers, grid ,cranes, wrongContainers);
//            moveSingleWrongContainer(containerToMove, grid ,cranes, wrongContainers);
        }
    }


    public static boolean isFinished(Containers containers){
        return containers.isFinished();
    }

    public static void main(String[] args) throws IOException, ParseException {
//        File initialYardFile = new File("src/main/instances/terminal22_1_100_1_10.json");
//        File targetYardFile = new File("src/main/instances/terminal22_1_100_1_10target.json");
        File initialYardFile = new File("src/main/instances/6t/targetTerminal_10_10_3_1_100.json");
        File targetYardFile = new File("src/main/instances/6t/Terminal_10_10_3_1_100.json");
        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();
        Containers containers = instance.getContainers();
        Grid grid = instance.getGrid();
        Cranes cranes = instance.getCranes();

        List<List<Container>> wrongContainers = containers.getWrongContainers(grid);

        while(!isFinished(containers)){
            System.out.println("press enter to continue");
            sc.nextLine();
            wrongContainers = containers.getWrongContainers(grid);
            moveContainer(containers, grid ,cranes, wrongContainers);
            grid.update(movements.getLastMovement());
        }

    }
}
