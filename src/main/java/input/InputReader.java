package input;

import container.Container;
import crane.Crane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import park.TargetYard;
import park.Yard;
import slot.Slot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputReader {
    private File initialYardFile;
    private File targetYardFile;
    private JSONObject initialYardJsonObject;
    private JSONObject targetYardJsonObject;

    public InputReader(File initialYardFile, File targetYardFile) throws IOException, ParseException {
        this.initialYardFile = initialYardFile;
        this.targetYardFile = targetYardFile;
        initialYardJsonObject = (JSONObject) new JSONParser().parse(new FileReader(initialYardFile));
        targetYardJsonObject = (JSONObject) new JSONParser().parse(new FileReader(targetYardFile));
    }

    public Map<Integer, Slot> makeGrid() {
        JSONArray slots = (JSONArray) initialYardJsonObject.get("slots");
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

    public Map<Integer, Container> makeContainerMap() {
        Map<Integer, Container> containerMap = new HashMap<>();
        JSONArray containers = (JSONArray) initialYardJsonObject.get("containers");
        Iterator<JSONObject> itr = containers.iterator();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            Container c = new Container((int) (long) j.get("id"), (int) (long) j.get("length"));
            containerMap.put(c.getId(), c);
        }
        return containerMap;
    }

    public Map<Integer, Crane> makeCraneMap() {
        Map<Integer, Crane> craneMap = new HashMap<>();
        JSONArray cranes = (JSONArray) initialYardJsonObject.get("cranes");
        Iterator<JSONObject> itr = cranes.iterator();
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
        return craneMap;
    }

    public TargetYard makeTargetYard() {
        int maxHeight = (int) (long) targetYardJsonObject.get("maxheight");
        JSONArray assignments = (JSONArray) targetYardJsonObject.get("assignments");
        Iterator<JSONObject> itr = assignments.iterator();
        Map<Integer, Integer> assignmentsMap = new HashMap<>();
        while (itr.hasNext()) {
            JSONObject j = itr.next();
            assignmentsMap.put((int) (long) j.get("container_id"), (int) (long) j.get("slot_id"));
        }
        return new TargetYard(maxHeight, assignmentsMap);
    }

    public Yard getYard() throws IOException, ParseException {
        Map<Integer, Slot> initialGrid = makeGrid();
        Map<Integer, Container> containerMap = makeContainerMap();

        JSONArray assignments = (JSONArray) initialYardJsonObject.get("assignments");
        Iterator<JSONObject> itr1 = assignments.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            int containerId = (int) (long) j.get("container_id");
            int slotId = (int) (long) j.get("slot_id");
            Container container = containerMap.get(containerId);
            List<Integer> allSlotIds = new ArrayList<>();
            for (int i = slotId; i < slotId + container.getLength(); i++) {
                initialGrid.get(i).addContainer(container);
                allSlotIds.add(i);

            }
            container.setSlotId(allSlotIds);
            containerMap.put(containerId, container);
        }

        return new Yard(initialGrid, makeCraneMap(), containerMap);
    }

    public Instance getInstance() throws IOException, ParseException {
        return new Instance(getYard(), makeTargetYard());
    }
}
