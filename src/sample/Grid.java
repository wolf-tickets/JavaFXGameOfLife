package sample;

import java.util.*;

/**
 * Grid is the class of objects that can be drawn as a playfield for the current
 * iteration of the game. Each iteration produces a new grid
 *
 * @see #
 */
public class Grid {

    private int PADDING = 3; //cell spacing in pixels
    private int CELLSIZE = 10; //cell size in pixels (10 x 10, eg)

    private int width; //grid width in cells
    private int height; //grid height in cells


    private Map<Coordinates, Boolean> cellMap; //map of cells to grid coordinates


    /**
     * Constructor for a new grid with all cells set to off.
     *
     * @param width:  width of grid in cells
     * @param height: height of grid in cells
     */
    public Grid(int width, int height) {

        this.width = width;
        this.height = height;
        this.cellMap = initGrid ( width, height );

        assert cellMap.size () == (width * height);


    }

    /**
     * Constructor for a grid with a specified cell map passed in as the third argument.
     *
     * @param width:  width of grid in cells
     * @param height: height of grid in cells
     * @param cells:  map of cells to on/off values
     * @see #cellMap
     */
    public Grid(int width, int height, Map<Coordinates, Boolean> cells) {
        this.width = width;
        this.height = height;
        this.cellMap = cells;

        assert cellMap.size () == (width * height);
    }


    /**
     * Creates an initial cell map for a new grid. All cells are off. This
     * method is called by the Grid constructor when no cell map is passed as
     * an argument.
     *
     * @param width:  Width of grid in cells.
     * @param height: Height of grid in cells.
     * @return: the initial cell map with all cells set to off
     * @see #Grid(int, int)
     */
    private Map<Coordinates, Boolean> initGrid(int width, int height) {
        int numCells = (width * height);

        Map<Coordinates, Boolean> newCellMap = new TreeMap<Coordinates, Boolean> ();

        for (int i = 0; i < numCells; i++) {

            Coordinates coords = cellPosition ( i );

            newCellMap.put ( coords, false );
        }

        return newCellMap;

    }

    /**
     * Creates the next iteration of the grid by applying the Game Of Life rules:
     * 1. Any live cell with fewer than two live neighbours dies
     * 2. Any live cell with more than three live neighbours dies
     * 3. Any live cell with two or three live neighbours survives to the next iteration
     * 4. Any dead cell with exactly three live neighbours comes to life
     * Grid wraps on itself in both dimensions
     *
     * @param oldGrid The current grid to be iterated
     * @return the next iteration of the grid
     * @see <a href="http://www.conwaylife.com/wiki/Conway%27s_Game_of_Life">Conway's Game of Life</a>
     */
    public Grid iterateGrid(Grid oldGrid) {

        Map<Coordinates, Boolean> newCellMap = new LinkedHashMap<> ();

        for (int i = 0; i < oldGrid.width; i++) {
            for (int j = 0; j < oldGrid.height; j++) {
                Coordinates cellCoords = new Coordinates ( i, j );
                int n = cellNeighbours ( oldGrid, cellCoords );
                if (oldGrid.cellMap.get ( cellCoords )) {
                    if (n < 2 || n > 3) {
                        newCellMap.put ( cellCoords, false );
                    } else {
                        newCellMap.put ( cellCoords, true );
                    }
                } else {
                    if (n == 3) {
                        newCellMap.put ( cellCoords, true);
                    } else {
                        newCellMap.put ( cellCoords,  false );
                    }

                }

            }
        }
        return new Grid ( oldGrid.width, oldGrid.height, newCellMap );
    }


    /**
     * Counts the number of live neighbour for the cell at the given coordinates on the grid.
     *
     * @param g The current grid
     * @param c The coordinates of the cell
     * @return the number of live neighbours
     */
    private int cellNeighbours(Grid g, Coordinates c) {

        List<Coordinates> neighbourCoords = new ArrayList<Coordinates> ();

        neighbourCoords.add ( new Coordinates ( c.x - 1, c.y - 1 ) );
        neighbourCoords.add ( new Coordinates ( c.x, c.y - 1 ) );
        neighbourCoords.add ( new Coordinates ( c.x + 1, c.y - 1 ) );
        neighbourCoords.add ( new Coordinates ( c.x - 1, c.y ) );
        neighbourCoords.add ( new Coordinates ( c.x + 1, c.y ) );
        neighbourCoords.add ( new Coordinates ( c.x - 1, c.y + 1 ) );
        neighbourCoords.add ( new Coordinates ( c.x, c.y + 1 ) );
        neighbourCoords.add ( new Coordinates ( c.x + 1, c.y + 1 ) );

        int neighbourCount = 0;

        for (Coordinates neighbour : neighbourCoords) {
            if (g.cellMap.containsValue ( neighbour ) && g.cellMap.get ( neighbour )) {

                neighbourCount++;
            }

        }
        return neighbourCount;
    }


    /**
     * Returns the x, y coordinates of the grid position of a numbered cell.
     * Called by the initGrid method to determine coordinate mapping for each cell.
     *
     * @param cellNumber
     * @return Coordinates
     * @see #initGrid(int, int)
     */
    private Coordinates cellPosition(int cellNumber) {
        return new Coordinates ( (cellNumber % width), (cellNumber / height) );
    }

    public int getCELLSIZE() {
        return CELLSIZE;
    }

    public void setCELLSIZE(int CELLSIZE) {
        this.CELLSIZE = CELLSIZE;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Map<Coordinates, Boolean> getCellMap() {
        return cellMap;
    }

    /**
     * Overridden toString method provides a well-formatted list of the cells
     * in a grid with coordinates and on/off value.
     *
     * @return String with list of cells.
     */
    @Override
    public String toString() {
        List<String> coordList = new ArrayList<String> ();
        for (Coordinates c : this.cellMap.keySet ()) {
            coordList.add ( "(" + c.toString () + cellMap.get ( c ) + ")" );
        }
        return coordList.toString ();
    }
}


