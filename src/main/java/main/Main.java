package main;

import input.InputReader;
import org.json.simple.parser.ParseException;
import park.Yard;
import point.Slot;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("src/main/instances/terminal_4_3.json");
        InputReader inputReader = new InputReader(file);
        Yard yard = inputReader.getYard();
        Map<Integer, Slot> initialGrid = yard.getGrid();

        System.out.println(initialGrid.toString());
    }
}
