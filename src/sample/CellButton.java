package sample;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Objects;

class CellButton extends Button implements Comparable<CellButton> {

    private static String COLOUR_OFF = "-fx-background-color: linear-gradient(darkgreen, royalblue)";
    private static String COLOUR_ON = "-fx-background-color: linear-gradient(fuchsia, deepskyblue)";
    private static double CELL_SIZE = 15;
    private static final Shape CELL_SHAPE = new Circle(CELL_SIZE / 2);
    private final int yPosition;
    private final int xPosition;
    private boolean on;


    CellButton(int x, int y) {

        on = false;
        xPosition = x;
        yPosition = y;
        this.setShape(CELL_SHAPE);
        this.setStyle(COLOUR_OFF);
        this.setOnAction(value -> this.reverseState());
        this.setOnMouseDragOver(value -> this.reverseState());
        setPrefSize(CELL_SIZE, CELL_SIZE);
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);


    }

    void reverseState() {


        if (!this.on) {
            this.on = true;
            this.setStyle(COLOUR_ON);
        } else {
            this.on = false;
            this.setStyle(COLOUR_OFF);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellButton that = (CellButton) o;
        return yPosition == that.yPosition &&
                xPosition == that.xPosition;
    }

    @Override
    public int hashCode() {

        return Objects.hash(yPosition, xPosition);
    }

    @Override
    public String toString() {
        return "CellButton{" +
                "yPosition=" + yPosition +
                ", xPosition=" + xPosition +
                '}';
    }

    @Override
    public int compareTo(CellButton o) {
        if (this.yPosition > o.yPosition) {
            return 1;
        } else if (this.yPosition < o.yPosition)
            return -1;
        else return Integer.compare(this.xPosition, o.xPosition);
    }

    int getXPosition() {
        return xPosition;
    }

    int getYPosition() {
        return yPosition;
    }

    boolean isOn() {
        return on;
    }
}
