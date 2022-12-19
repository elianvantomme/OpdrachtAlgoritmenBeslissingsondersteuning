package main;

import container.Container;
import container.Containers;
import crane.Crane;
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

    public static void moveSingleWrongContainer(Container container, Instance instance){

    }

    public static void moveStackOfContainers(){

    }

    public static void moveContainer(Instance instance, List<List<Container>> wrongContainers){
        Container containerToMove = wrongContainers.get(0).get(0);
        // aparte methoden als er wel of niet containers bovenop staan die we moeten eerst verplaatsen
        if(wrongContainers.get(0).size()==1){
//            moveSingleWrongContainer();
        }else{
            moveStackOfContainers();
//            moveSingleWrongContainer();
        }
    }

    public static boolean isFinished(List<List<Container>> wrongContainers){
        return wrongContainers.isEmpty();
    }

    public static void main(String[] args) throws IOException, ParseException {
        File initialYardFile = new File("src/main/instances/instances1/5t/TerminalB_20_10_3_2_160.json");
        File targetYardFile = new File("src/main/instances/instances1/5t/targetTerminalB_20_10_3_2_160.json");
        InputReader inputReader = new InputReader(initialYardFile, targetYardFile);
        Instance instance = inputReader.getInstance();
        Containers containers = instance.getContainers();
        Grid grid = instance.getGrid();

        List<List<Container>> wrongContainers = containers.getWrongContainers(grid);
        while(!isFinished(wrongContainers)){
            System.out.println("press enter to continue");
            sc.nextLine();
            wrongContainers = containers.getWrongContainers(grid);
            moveContainer(instance, wrongContainers);
        }

    }
}
