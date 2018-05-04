package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class provides the individual "cells" for the game grid. Extends the JavaFX Button class.
 */
class CellButton extends Button implements Comparable<CellButton> {

    private static final String COLOUR_OFF = "-fx-background-color: linear-gradient(black, darkgreen)";
    private static final String COLOUR_ON = "-fx-background-color: linear-gradient(fuchsia, deepskyblue)";
    private static final double CELL_SIZE = 15;
    private static final Circle CELL_SHAPE = new Circle(CELL_SIZE / 2);
    private final int yPosition;
    private final int xPosition;
    private final ArrayList<Integer> neighbours;
    private boolean on;

    /**
     * Constructor: creates a new CellButton
     *
     * @param x        X position on grid
     * @param y        Y position on grid
     * @param gridSize dimension of the grid
     */
    CellButton(int x, int y, int gridSize) {

        on = false;
        xPosition = x;
        yPosition = y;
        this.setShape(CELL_SHAPE);
        this.setStyle(COLOUR_OFF);
        this.setOnAction(value -> this.reverseState());
        setPrefSize(CELL_SIZE, CELL_SIZE);
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        neighbours = this.getNeighbours(gridSize);
        //TODO: get mouse drag working to turn cells on.
        EventHandler<MouseDragEvent> mouseDragHandler = event -> this.reverseState();
        this.setOnMouseDragOver(mouseDragHandler);


    }

    /**
     * Computes coordinates which wrap around the edges of the grid. Used to compute the list of a cell's neighbours.
     *
     * @param c        the coordinate
     * @param gridSize the dimension of the grid
     * @return if c is -1, returns grid size, and if c is equal to the grid size, returns 0.
     * (i.e. the grid wraps around from top to bottom, left to right, and vice versa
     */
    //TODO: make wrapping optional
    private static int wrapCoordinate(int c, int gridSize) {
        int z;
        if (c == -1) {
            z = gridSize - 1;
        } else if (c == gridSize) {
            z = 0;
        } else z = c;
        return z;
    }

    static double getCellSize() {
        return CELL_SIZE;
    }

    /**
     * Creates a list of the cell's eight neighbours at construction time, rather than on each grid iteration.
     * This improves the runGame() method's performance significantly.
     *
     * @param gridSize the grid size parameter is passed to the wrapCoordinates() helper.
     * @return a list of the cell's neighbours.
     */
    private ArrayList<Integer> getNeighbours(int gridSize) {
        int x;
        int y;

        ArrayList<Integer> neighbours = new ArrayList<>();

        for (int i = this.yPosition - 1; i < this.yPosition + 2; i++) {
            for (int j = xPosition - 1; j < xPosition + 2; j++) {
                y = wrapCoordinate(i, gridSize);
                x = wrapCoordinate(j, gridSize);
                neighbours.add(x + (gridSize * y));

            }
        }
        neighbours.remove(4); //index 4 is the cell itself, which should not be counted.
        return neighbours;
    }

    /**
     * Reverses the state of the cell. If the cell is off, it will be set to on and
     * its colour changed appropriately, and vice versa.
     */

    void reverseState() {


        if (!this.on) {
            this.on = true;
            this.setStyle(COLOUR_ON);
        } else {
            this.on = false;
            this.setStyle(COLOUR_OFF);
        }
//TODO: add fades:help
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellButton that = (CellButton) o;
        return yPosition == that.yPosition &&
                xPosition == that.xPosition;
    }

    @Override
    public int hashCode() {

        return Objects.hash(yPosition, xPosition);
    }

    @Override
    public String toString() {
        return "CellButton{" +
                "yPosition=" + yPosition +
                ", xPosition=" + xPosition +
                '}';
    }

    @Override
    public int compareTo(CellButton o) {
        if (this.yPosition > o.yPosition) {
            return 1;
        } else if (this.yPosition < o.yPosition)
            return -1;
        else return Integer.compare(this.xPosition, o.xPosition);
    }

    boolean isOn() {
        return on;
    }

    ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

}
