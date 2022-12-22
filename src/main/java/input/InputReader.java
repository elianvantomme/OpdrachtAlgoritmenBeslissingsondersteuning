package input;

import container.Container;
import container.Containers;
import crane.Crane;
import crane.Cranes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import grid.Grid;
import slot.Slot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputReader {
    private JSONObject initialYardJsonObject;
    private JSONObject targetYardJsonObject;
    private AlgorithmType type;

    public InputReader(File initialYardFile, File targetYardFile) throws IOException, ParseException {
        initialYardJsonObject = (JSONObject) new JSONParser().parse(new FileReader(initialYardFile));
        targetYardJsonObject = (JSONObject) new JSONParser().parse(new FileReader(targetYardFile));
        type = AlgorithmType.TRANSFORMTERMINAL;
    }

    public InputReader(File initialYardFile) throws IOException, ParseException {
        initialYardJsonObject = (JSONObject) new JSONParser().parse(new FileReader(initialYardFile));
        type = AlgorithmType.TRANSFORMHEIGHT;
    }

    public Grid makeGrid() {
        JSONArray slots = (JSONArray) initialYardJsonObject.get("slots");
        Map<Integer, Slot> gridMap = new HashMap<>();
        Iterator<JSONObject> itr = slots.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            Slot s = new Slot(
                    (int) (long) j.get("id"),
                    (int) (long) j.get("x"),
                    (int) (long) j.get("y")
            );
            gridMap.put(s.getId(), s);
        }
        int maxHeight = (int) (long) initialYardJsonObject.get("maxheight");
        int length = (int) (long) initialYardJsonObject.get("length");
        int width = (int) (long) initialYardJsonObject.get("width");
        if(type == AlgorithmType.TRANSFORMHEIGHT){
            int targetHeight = (int)(long) initialYardJsonObject.get("targetheight");
            return new Grid(gridMap, maxHeight, targetHeight, length, width);
        }
        else {
            return new Grid(gridMap, maxHeight, length, width);
        }
    }

    public Containers makeContainers(Grid grid) {
        Map<Integer, Container> containerMap = new HashMap<>();
        JSONArray containersJson = (JSONArray) initialYardJsonObject.get("containers");
        Iterator<JSONObject> itr = containersJson.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            Container c = new Container((int) (long) j.get("id"), (int) (long) j.get("length"));
            containerMap.put(c.getId(), c);
        }

        JSONArray initialAssignments = (JSONArray) initialYardJsonObject.get("assignments");
        Iterator<JSONObject> itr1 = initialAssignments.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            int containerId = (int) (long) j.get("container_id");
            int slotId = (int) (long) j.get("slot_id");
            Container container = containerMap.get(containerId);
            container.setSlotId(slotId);
            grid.putContainerOnSlot(container, slotId);
        }

        Containers containers = new Containers(containerMap);

        if(type == AlgorithmType.TRANSFORMTERMINAL) {
            JSONArray targetAssignments = (JSONArray) targetYardJsonObject.get("assignments");
            itr = targetAssignments.iterator();
            while (itr.hasNext()) {
                JSONObject j = itr.next();
                containers.setTargetSlotId((int) (long) j.get("container_id"), (int) (long) j.get("slot_id"));
            }
        }
        return containers;
    }


    public Cranes makeCranes() {
        Map<Integer, Crane> craneMap = new HashMap<>();
        JSONArray cranesJson = (JSONArray) initialYardJsonObject.get("cranes");
        Iterator<JSONObject> itr = cranesJson.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            craneMap.put((int) (long) j.get("id"), new Crane(
                    (int) (long) j.get("id"),
                    ((Number)j.get("x")).doubleValue(),
                    ((Number)j.get("y")).doubleValue(),
                    (int) (long) j.get("xmin"),
                    (int) (long) j.get("xmax"),
                    (int) (long) j.get("ymin"),
                    (int) (long) j.get("ymax"),
                    (int) (long) j.get("xspeed"),
                    (int) (long) j.get("yspeed")
            ));
        }
        Cranes cranes = new Cranes(craneMap);
        return cranes;
    }


    public Instance getInstance() throws IOException, ParseException {
        Grid grid = makeGrid();
        Containers containers = makeContainers(grid);
        Cranes cranes = makeCranes();
        return new Instance(grid, cranes, containers, type);
    }
}
