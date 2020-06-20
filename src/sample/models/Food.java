package sample.models;

import javafx.scene.shape.Rectangle;
import sample.FoodType;

public class Food extends Rectangle {

    public static int foodCounter=0;
    private int col, row;
    private FoodType foodType;

    public Food(int width, int height, int col, int row, FoodType foodType){
        if (foodType==FoodType.GOOD)
            foodCounter++;

        setWidth(width);
        setHeight(height);
        this.col = col;
        this.row = row;
        this.foodType = foodType;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public FoodType getFoodType() {
        return foodType;
    }

}
