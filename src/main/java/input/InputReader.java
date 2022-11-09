package input;

import container.Container;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import park.Yard;
import point.Slot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputReader {
    private File file;

    public InputReader(File file) {
        this.file = file;
    }

    public Yard getYard() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray slots = (JSONArray) jsonObject.get("slots");
        Map<Integer, Slot> grid = new HashMap<>();
        Iterator<JSONObject> itr1 = slots.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            Slot s = new Slot(
                    (int) (long) j.get("id"),
                    (int) (long) j.get("x"),
                    (int) (long) j.get("y")
            );
            grid.put(s.getId(), s);
        }

        Map<Integer, Container> containerMap = new HashMap<>();
        JSONArray containers = (JSONArray) jsonObject.get("containers");
        itr1 = containers.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            Container c = new Container((int) (long) j.get("id"), (int) (long) j.get("length"));
            containerMap.put(c.getId(), c);
        }

        JSONArray assignments = (JSONArray) jsonObject.get("assignments");
        itr1 = assignments.iterator();
        while (itr1.hasNext()) {
            JSONObject j = itr1.next();
            int containerId = (int) (long) j.get("container_id");
            JSONArray slot_id = (JSONArray) j.get("slot_id");
            for (int i = 0; i < slot_id.size(); i++) {
                int slotId = (int) (long) slot_id.get(i);
                grid.get(slotId).addContainer(containerMap.get(containerId));
            }
        }

        return new Yard(grid);
    }
}
