package sample.models;

import javafx.scene.shape.Rectangle;

public class SnakeBodyElement extends Rectangle {

    private int col, row;

    public SnakeBodyElement(int width, int height, int col, int row){
        setWidth(width);
        setHeight(height);
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
