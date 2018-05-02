package model;

import javafx.fxml.FXML;

/**
 * Takes over from the Main class once the play button is clicked on the title screen
 */

class GameController {

    /**
     * Calls the GameGrid constructor, passing the size value from the main screen's grid size slider,
     * and then assigns the GameGrid as the main scene's new root node.
     *
     * @param size The dimensions of the grid.
     */
    @FXML
    static void initializeGame(int size) {

        GameGrid gameGrid = new GameGrid(size);
        Main.getMainScene().setRoot(gameGrid);

    }

}
