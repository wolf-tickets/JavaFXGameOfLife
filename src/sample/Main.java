package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        Grid newGrid = new Grid ( 10, 10 );

        System.out.println ( newGrid.toString () );
        System.out.println ( newGrid.getCellMap ().size () );


        Grid twentyByTen = new Grid ( 20,10 );

        System.out.println ( twentyByTen.toString () );
        System.out.println ( "Size: " + twentyByTen.getCellMap ().size () );
    }
    //    private Graphics drawGrid(Grid grid) {
//
//        int gridWidth = grid.getWidth () * grid.getCELLSIZE ();
//        int gridHeight = grid.getHeight () * grid.getCELLSIZE ();
//        Map cellsToDraw = grid.getCellMap ();
//
//        Graphics gridtoDraw = null;
//
//
//        for (Coordinates c: grid.getCellMap ().keySet ()) {
//            fill3DRect ( (c.x *10), (c.y * 10), grid.getCELLSIZE (), grid.getCELLSIZE (), (Boolean) cellsToDraw.get (c) );
//
//        }
    private void setUpPhase() { //TODO: implement

    }

    private Grid nextGrid(Grid g) {
        return g.iterateGrid ( g );

    }

    private void drawGrid(Grid grid) {

        Map<Coordinates,Boolean> cellMap = grid.getCellMap ();
        for (Map.Entry cellEntry: cellMap.entrySet ()) {
            Coordinates cellCoords = (Coordinates) cellEntry.getKey ();
            int xPos = cellCoords.x;
            int yPos = cellCoords.y;
            boolean on = (boolean) cellEntry.getValue ();
            drawCell (grid, xPos, yPos, on );



        }}


        private void drawCell(Grid g, int x, int y, boolean on) {


        }
}
