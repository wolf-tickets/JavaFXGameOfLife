package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static int GRID_HEIGHT = 50;
    private static int GRID_WIDTH = 60;
    private static Stage gameStage = new Stage();
    public Label gameOfLife;

    public void setUpPhase() {

        GridPane setUp = populateCells();

        setUp.addRow(GRID_HEIGHT);


        Scene gameScene = new Scene(setUp);

        Button start = new Button("Start");

        setUp.add(start, GRID_WIDTH / 4, GRID_HEIGHT, 10, 1);
        start.setOnAction(value -> this.runGame(setUp, start));

        Button clear = new Button("Clear");
        setUp.add(clear, GRID_WIDTH / 2, GRID_HEIGHT, 10, 1);
        clear.setOnAction((actionEvent -> this.clearAllCells(setUp)));

        Button exit = new Button("Exit");
        setUp.add(exit, GRID_WIDTH / 4 * 3, GRID_HEIGHT, 10, 1);
        exit.setOnAction(value -> Platform.exit());


        gameStage.setResizable(true);
        gameStage.setScene(gameScene);
        gameStage.setTitle("Conway's Game of Life");
        gameStage.show();

    }

    private void clearAllCells(GridPane setUp) {

        for (Node n : setUp.getChildren()) {

            if ((n instanceof CellButton) && (((CellButton) n).isOn()))
                ((CellButton) n).reverseState();
        }
    }

    private void runGame(GridPane gridPane, Button stop) {


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), (actionEvent -> {
            evolveGrid(gridPane);
            gameStage.show();
        })));

        stop.setText("Stop");
        stop.setOnAction(value -> pausedGrid(gridPane, stop, timeline));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    private GridPane populateCells() {
        int rows = GRID_HEIGHT;
        int cols = GRID_WIDTH;
        GridPane setUp = new GridPane();
        setUp.setHgap(0);
        setUp.setVgap(0);
        setUp.setAlignment(Pos.CENTER);
        setUp.setStyle("-fx-background-color: linear-gradient(reflect, darkorange, mediumvioletred) ");


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button b = new CellButton(j, i);

                GridPane.setColumnIndex(b, j);
                GridPane.setRowIndex(b, i);
                setUp.getChildren().add(b);

            }
        }

        return setUp;
    }

    private void evolveGrid(GridPane gridPane) {
        final ArrayList<CellButton> oldCells = new ArrayList<>();

        for (Node n : gridPane.getChildren()) {
            if (n instanceof CellButton) {
                oldCells.add((CellButton) n);
            }
        }
        List<CellButton> switchButtons = new ArrayList<>();

        for (CellButton oldCell : oldCells) {

            int oldCellNeighbours = cellNeighbours(oldCell, oldCells);

            if (oldCell.isOn()) {
                if (oldCellNeighbours < 2 || oldCellNeighbours > 3) {
                    switchButtons.add(oldCell);
                }
            } else if (oldCellNeighbours == 3) {
                switchButtons.add(oldCell);
            }

        }
        for (CellButton c : switchButtons) {
            c.reverseState();

        }

    }

    private int cellNeighbours(CellButton cell, ArrayList<CellButton> cellList) {
        int neighbours = 0;
        int xPos = cell.getXPosition();
        int yPos = cell.getYPosition();
        int x;
        int y;


        for (int i = yPos - 1; i < yPos + 2; i++) {
            for (int j = xPos - 1; j < xPos + 2; j++) {
                if (i == -1) {
                    y = GRID_HEIGHT - 1;
                } else if (i == GRID_HEIGHT) {
                    y = 0;
                } else y = i;

                if (j == -1) {
                    x = GRID_WIDTH - 1;
                } else if (j == GRID_WIDTH) {
                    x = 0;
                } else x = j;

                if (cellList.get(x + (GRID_WIDTH * y)).isOn())
                    neighbours++;

            }

        }

        if (cell.isOn()) {
            neighbours = neighbours - 1;
        }

        return neighbours;
    }

    private void pausedGrid(GridPane gridPane, Button start, Timeline t) {

        t.stop();
        start.setText("Start");
        start.setOnAction(value -> runGame(gridPane, start));


    }

}
