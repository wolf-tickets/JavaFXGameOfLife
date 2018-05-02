package model;

import javafx.fxml.FXML;

public class GameController {


    @FXML
    static void initializeGame(int size) {

        GameGrid gameGrid = new GameGrid(size);
        Main.getMainScene().setRoot(gameGrid);

    }

}
