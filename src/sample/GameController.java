package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class GameController {
    public Label gameOfLife;

    public void playGame(ActionEvent actionEvent) {
        gameOfLife.setText("Game Of Life");
    }
}
