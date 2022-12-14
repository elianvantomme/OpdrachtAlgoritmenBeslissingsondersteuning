package movement;

import grid.Grid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movements {
    private List<Movement> movementList;

    public Movements(){
        movementList = new ArrayList<>();
    }
    public void addContainerMovement(Movement movement, Grid grid){
        System.out.println("\n"+movement +"\n");
        movementList.add(movement);
        grid.update(movement);
    }

    public void addEmptyMovement(Movement movement){
        System.out.println("\n"+movement +"\n");
        movementList.add(movement);
    }

    public Movement getLastMovement(){
        return movementList.get(movementList.size()-1);
    }

    public void print(){
        System.out.println("craneId,containerId,startTime,endTime,xPickup,yPickup,xDropOff,yDropOff");
        for(Movement m : movementList){
            System.out.println(m);
        }
    }

    public void printToFile(String outputFileString) throws IOException {
        FileWriter fw = new FileWriter(outputFileString);
        fw.write("craneId,containerId,startTime,endTime,xPickup,yPickup,xDropOff,yDropOff \n");
        for (Movement m : movementList){
            fw.write(m.toString() + "\n");
        }
        fw.close();
    }
}
