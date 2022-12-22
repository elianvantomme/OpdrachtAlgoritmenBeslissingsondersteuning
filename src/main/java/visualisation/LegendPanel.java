package visualisation;

import grid.Grid;

import javax.swing.*;
import java.awt.*;

public class LegendPanel extends JPanel {
    private static final int RECT_WIDTH = 20; // width of each color rectangle in pixels
    private static final int RECT_HEIGHT = 20; // height of each color rectangle in pixels
    private static final int PADDING = 10; // padding between elements in pixels
    private static final int FONT_SIZE = 12; // font size for text labels

    private Grid grid; // the grid to visualize
    private int maxHeight;

    public LegendPanel(Grid grid) {
        this.grid = grid;
        this.maxHeight = grid.getMaxHeight();
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = PADDING; // x-coordinate for drawing elements
        int y = PADDING; // y-coordinate for drawing elements

        for (int i = 0; i <= grid.getMaxHeight(); i++) {
            // draw the color rectangle
            g.setColor(getHeatMapColor(i));
            g.fillRect(x, y, RECT_WIDTH, RECT_HEIGHT);

            // draw the text label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
            g.drawString(Integer.toString(i), x + RECT_WIDTH + PADDING, y + RECT_HEIGHT);

            // update the y-coordinate for the next element
            y += RECT_HEIGHT + PADDING;
        }
    }

    // returns a color for the given height, with lower heights being darker and higher heights being lighter
    private Color getHeatMapColor(int height) {
        float intensity = 1 - (float) height / maxHeight;
        return new Color(intensity, intensity, intensity);
    }
}

