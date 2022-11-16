package input;

import container.Container;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import park.Yard;
import point.Slot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputReader {
    private File file;
    private JSONObject jsonObject;

    public InputReader(File file) throws IOException, ParseException {
        this.file = file;
        jsonObject = (JSONObject) new JSONParser().parse(new FileReader(file));
    }

    public Map<Integer, Slot> makeGrid(){
        JSONArray slots = (JSONArray) jsonObject.get("slots");
        Map<Integer, Slot> grid = new HashMap<>();
        Iterator<JSONObject> itr = slots.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            Slot s = new Slot(
                    (int) (long) j.get("id"),
                    (int) (long) j.get("x"),
                    (int) (long) j.get("y")
            );
            grid.put(s.getId(), s);
        }
        return grid;
    }

    public Map<Integer, Container> makeContainerMap(){
        Map<Integer, Container> containerMap = new HashMap<>();
        JSONArray containers = (JSONArray) jsonObject.get("containers");
        Iterator<JSONObject> itr = containers.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            Container c = new Container((int) (long) j.get("id"), (int) (long) j.get("length"));
            containerMap.put(c.getId(), c);
        }
        return containerMap;
    }

//    public Yard makeYard(JSONArray assignments, Map<Integer, Slot> grid, Map<Integer, Container> containerMap){
//        Iterator<JSONObject> itr = assignments.iterator();
//        while (itr.hasNext()) {
//            JSONObject j = itr.next();
//            int containerId = (int) (long) j.get("container_id");
//            JSONArray slot_id = (JSONArray) j.get("slot_id");
//            for (int i = 0; i < slot_id.size(); i++) {
//                int slotId = (int) (long) slot_id.get(i);
//                grid.get(slotId).addContainer(containerMap.get(containerId));
//            }
//        }
//        return new Yard(grid);
//    }

    public Yard getYard() throws IOException, ParseException {
        Map<Integer, Slot> initialGrid = makeGrid();
        Map<Integer, Slot> finalGrid = new HashMap<>(initialGrid);
        Map<Integer, Container> containerMap = makeContainerMap();



        JSONArray assignments = (JSONArray) jsonObject.get("assignments");
        Iterator<JSONObject> itr1 = assignments.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            int containerId = (int) (long) j.get("container_id");
            JSONArray slot_id = (JSONArray) j.get("slot_id");
            for (int i = 0; i < slot_id.size(); i++) {
                int slotId = (int) (long) slot_id.get(i);
                initialGrid.get(slotId).addContainer(containerMap.get(containerId));
            }
        }



        return new Yard(initialGrid);
    }
}
