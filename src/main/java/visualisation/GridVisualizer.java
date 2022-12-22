package visualisation;

import grid.Grid;
import slot.Slot;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GridVisualizer extends JPanel {
    private static final int SLOT_SIZE = 50; // size of each slot in pixels
    private static final int PADDING = 10; // padding around the grid in pixels

    private Grid grid; // the grid to visualize
    private int maxHeight;
    LegendPanel legendPanel;

    public GridVisualizer(Grid grid) {
        this.grid = grid;
        this.maxHeight = grid.getMaxHeight();
        this.legendPanel = new LegendPanel(grid);
        legendPanel.setPreferredSize(new Dimension(100,200));
        createAndShowGUI();
    }

    @Override
    public void paintComponent(Graphics g) {
        // get the dimensions of the grid
        int numRows = grid.getWidth();
        int numColumns = grid.getLength();

        // determine the size of the visualization
        int width = numColumns * SLOT_SIZE + 2 * PADDING;
        int height = numRows * SLOT_SIZE + 2 * PADDING;

        // create a buffered image to draw on
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics imgGraphics = image.getGraphics();

        for(Slot s : grid.getGrid().values()){
            // determine the position of the slot in pixels
            int x = s.getX() * SLOT_SIZE + PADDING;
            int y = numRows - s.getY() - 1;
            y = y * SLOT_SIZE + PADDING;

            // draw the background of the slot
            imgGraphics.setColor(getHeatMapColor(s.getHeight()));
            imgGraphics.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);

            // draw the outline of the slot
            imgGraphics.setColor(Color.BLACK);
            imgGraphics.drawRect(x, y, SLOT_SIZE, SLOT_SIZE);
        }

        // draw the buffered image on the panel
        g.drawImage(image, 0, 0, null);
    }

    // returns a color for the given height, with lower heights being darker and higher heights being lighter
    private Color getHeatMapColor(int height) {
        float intensity = 1 - (float) height / maxHeight;
        return new Color(intensity, intensity, intensity);
    }

    // creates a window to display the grid visualization
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Grid Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// use GridBagLayout for the frame
        frame.setLayout(new GridBagLayout());

// create GridBagConstraints for the grid panel
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.fill = GridBagConstraints.BOTH;
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 1.0;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;

// add the grid panel to the frame with the specified constraints
        frame.add(this, gridConstraints);

// create GridBagConstraints for the legend panel
        GridBagConstraints legendConstraints = new GridBagConstraints();
        legendConstraints.fill = GridBagConstraints.VERTICAL;
        legendConstraints.weightx = 0.0;
        legendConstraints.weighty = 1.0;
        legendConstraints.gridx = 1;
        legendConstraints.gridy = 0;

// add the legend panel to the frame with the specified constraints
        frame.add(legendPanel, legendConstraints);

        frame.setPreferredSize(new Dimension(1500, 1000));
        frame.pack();
        frame.setVisible(true);
    }

    // updates the visualization by repainting the panel
    public void update() {
        repaint();
    }
}

