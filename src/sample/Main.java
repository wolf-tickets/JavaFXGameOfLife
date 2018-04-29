package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainScene = new Scene(root, 800, 800, Color.ANTIQUEWHITE);

        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}