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

    public static void moveSingleWrongContainer(Container containerToMove, Grid grid ,Cranes cranes){
        /*
        STAP 1: kijken voor usable kraan
         */
        Slot initialSlot = grid.getSlot(containerToMove.getSlotId());
        Slot targetSlot = grid.getSlot(containerToMove.getTargetSlotId());
        List<Crane> idealCranes = cranes.findIdealCranes(initialSlot, targetSlot);
        if(!idealCranes.isEmpty()){
            Crane idealCrane = idealCranes.get(0);
            moveWithIdealContainer(initialSlot, targetSlot, idealCrane, containerToMove);

        }
        else{
            // pickup crane en afzet crane ophalen
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

    public static boolean isFinished(List<List<Container>> wrongContainers){
        return wrongContainers.isEmpty();
    }

    public static void main(String[] args) throws IOException, ParseException {
        File initialYardFile = new File("src/main/instances/terminal22_1_100_1_10.json");
        File targetYardFile = new File("src/main/instances/terminal22_1_100_1_10target.json");
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
