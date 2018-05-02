package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The grid of "cells" that make up the playing area. Extends the JavaFX GridPane class.
 *
 * @see javafx.scene.layout.GridPane
 */

class GameGrid extends GridPane {

    private final Button startStopButton; //class-level declaration: methods change it from stop to start and vice-versa
    private Timeline gameTimeline;
    private ArrayList<CellButton> cellList;

    /**
     * Constructor for the GameGrid class. Takes an integer dimension value and constructs
     * a square grid of CellButtons, plus a Button Bar with the stop/start button, clear button, and exit button.
     *
     * @param gridSize the dimension of the square grid.
     * @see CellButton
     */

    GameGrid(int gridSize) {

        populateCells(gridSize);
        setAlignment(Pos.CENTER);

        startStopButton = new Button("Start");
        startStopButton.setDefaultButton(true);
        startStopButton.setOnAction(value -> runGame());
        startStopButton.setPadding(new Insets(10));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(value -> clearAllCells());
        clearButton.setPadding(new Insets(10));

        Button exitButton = new Button("Exit");
        exitButton.setCancelButton(true);
        exitButton.setOnAction(e -> Platform.exit());
        exitButton.setPadding(new Insets(10));

        ButtonBar controlButtons = new ButtonBar();

        controlButtons.getButtons().add(startStopButton);
        controlButtons.getButtons().add(clearButton);
        controlButtons.getButtons().add(exitButton);
        controlButtons.setPadding(new Insets(10));

        this.add(controlButtons, 0, gridSize + 1, gridSize, 1);
//TODO: add speed slider to game grid
        setPrefSize(CellButton.getCellSize() * gridSize * 1.2, CellButton.getCellSize() * gridSize * 1.2);
        setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);


    }

    /**
     * Populates the GameGrid with the required number of CellButtons (gridSize * gridSize).
     * Also creates a list of the cells for easier access. The cells are added in the order they are created and
     * can therefore be accessed by their list index (e.g. the cell at x:5, y:8 will be at index 85).
     *
     * @param gridSize The dimension of the cell grid.
     */

    private void populateCells(int gridSize) {

        cellList = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                CellButton b = new CellButton(j, i, gridSize);
                this.add(b, j, i);
                cellList.add(b);
            }
        }
    }

    /**
     * Executes indefinitely while the game is running, on a Timeline whose Keyframe can be modified to change the
     * speed of the game. The method will continue to run every Keyframe until the stop or exit button is pressed.
     */

    private void runGame() {

        gameTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (actionEvent -> evolveGrid())));
        startStopButton.setText("Stop");
        startStopButton.setOnAction(value -> pauseGame());
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();

    }

    /**
     * The main logic of Conway's Game Of Life. Iterates the grid according to the rules: any live cell with less than
     * two or more than three live neighbours dies; any dead cell with exactly three live neighbours comes to life.
     * This method is called by the runGame() method every keyframe.
     */
    private void evolveGrid() {


        List<CellButton> switchButtons = new ArrayList<>();

        for (CellButton cell : cellList) {
            int liveNeighbours = getLiveNeighbours(cell.getNeighbours());
            if (cell.isOn()) {
                if (liveNeighbours < 2 || liveNeighbours > 3) {
                    switchButtons.add(cell);
                }
            } else if (liveNeighbours == 3) {
                switchButtons.add(cell);
            }
        }

        for (CellButton c : switchButtons) {
            c.reverseState();
        }
    }

    /**
     * Counts the live neighbours for each cell in the grid.
     *
     * @param list List of the cell's neighbours (which is a field of the CellButton object).
     * @return The number of live neighbours for the given cell.
     */
    private int getLiveNeighbours(ArrayList<Integer> list) {

        int count = 0;
        for (Integer i : list)
            if (cellList.get(i).isOn())
                count++;
        return count;

    }

    /**
     * Pauses the game when the stop button is pressed. Changes the stop button to a start button.
     */
    private void pauseGame() {

        gameTimeline.stop();
        startStopButton.setText("Start");
        startStopButton.setOnAction(value -> runGame());

    }

    /**
     * Sets all cells to off.
     */
    private void clearAllCells() {

        for (CellButton c : cellList) {
            if (c.isOn()) c.reverseState();
        }
    }

}
