package model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * The Game Of Life is a cellular automaton invented by the mathematician John Conway in 1970. This is a simple
 * implementation in Java using the JavaFX UI library.
 *
 * @author Al Smith   al@whitefootsmiith.com
 * @see <a href=http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life> Wikipedia.</a>
 * <p>
 * Because the Game is Turing-complete, there is no algorithm that can predict whether a given starting pattern
 * will run forever or eventually settle into a periodic steady state - this is a corollary of the "halting problem".
 * Have fun!
 */


public class Main extends Application {

    private static final Parent root = new Parent() {
    };
    private static final Scene mainScene = new Scene(root);
    private static Stage gameStage = new Stage(StageStyle.DECORATED);
    @FXML
    private Slider gridSizeSlider;

    static Scene getMainScene() {
        return mainScene;
    }

    @Override
    public void start(Stage mainStage) throws IOException {

        Parent titleRoot = FXMLLoader.load(getClass().getResource("../ui/Main.fxml"));
        mainScene.setRoot(titleRoot);
        gameStage = mainStage;
        gameStage.setResizable(true);
        gameStage.setTitle("Conway's Game Of Life");
        gameStage.setScene(mainScene);
        gameStage.show();


    }

    /**
     * Called when the Play button is pressed. Passes the value from the grid size slider to the GameController class,
     * and resizes the main window based on the size of the CellButtons and the dimensions of the grid.
     */
    @FXML
    protected void startGame() {

        GameController.initializeGame((int) gridSizeSlider.getValue());
        gameStage.setMinHeight(gridSizeSlider.getValue() * CellButton.getCellSize() * 1.2);
        gameStage.setMinWidth(gridSizeSlider.getValue() * CellButton.getCellSize());
//TODO: get device display area and set cell size/window size accordingly
//TODO: migrate other classes to FXML and style with CSS
//TODO: JavaDoc all classes/methods

    }

}
