package crane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Movements {
    private List<Movement> movementList;

    public Movements(){
        movementList = new ArrayList<>();
    }
    public void addMovement(Movement movement){
        movementList.add(movement);
    }
    public Movement getLastMovement(){
        return movementList.get(movementList.size()-1);
    }

}
