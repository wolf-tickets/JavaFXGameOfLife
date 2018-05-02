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

class GameGrid extends GridPane {

    private Timeline gameTimeline;
    private Button startStopButton;
    private ArrayList<CellButton> cellList;

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


    private void runGame() {

        gameTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), (actionEvent -> evolveGrid())));
        startStopButton.setText("Stop");
        startStopButton.setOnAction(value -> pauseGame());
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();

    }

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

    private int getLiveNeighbours(ArrayList<Integer> list) {

        int count = 0;
        for (Integer i : list)
            if (cellList.get(i).isOn())
                count++;
        return count;

    }

    private void pauseGame() {

        gameTimeline.stop();
        startStopButton.setText("Start");
        startStopButton.setOnAction(value -> runGame());

    }

    private void clearAllCells() {

        for (CellButton c : cellList) {
            if (c.isOn()) c.reverseState();
        }
    }

}
