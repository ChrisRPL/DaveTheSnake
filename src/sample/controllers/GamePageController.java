package sample.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.models.Food;
import sample.FoodType;
import sample.MoveDirections;
import sample.models.SnakeBodyElement;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GamePageController {

    private int cols, rows;
    private LinkedList<SnakeBodyElement> snakeBody;
    private int score, time=0;
    private Food goodFood, badFood;
    private boolean gameOver;
    private Timeline animation, timeAnimation;
    private KeyFrame frame, timeFrame;

    @FXML
    private GridPane board;
    private MoveDirections direction;

    @FXML
    private Label scoreBoard;

    @FXML
    private Label timeBoard;

    @FXML
    private Button homeBtn;

    @FXML
    private VBox container;

    @FXML
    void initialize(){

        snakeBody = new LinkedList<>();
        SnakeBodyElement head = new SnakeBodyElement(50, 50, 6, 6);
        head.getStyleClass().add("snakeBody");
        snakeBody.add(head);
        board.add(head, 6, 6);
        direction = MoveDirections.LEFT;
        this.goodFood = new Food(30, 30, 3, 3, FoodType.GOOD);
        goodFood.getStyleClass().add("goodFood");
        board.add(goodFood, goodFood.getCol(), goodFood.getRow());
        GridPane.setHalignment(goodFood, HPos.CENTER);

        container.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.Q,
                    KeyCombination.CONTROL_ANY, KeyCombination.SHIFT_ANY);

            if (keyComb.match(key))
                homeBtn.fire();

            if (key.getCode() == KeyCode.UP && direction != MoveDirections.DOWN) {
                direction = MoveDirections.UP;
            }
            if (key.getCode() == KeyCode.LEFT && direction != MoveDirections.RIGHT) {
                direction = MoveDirections.LEFT;
            }
            if (key.getCode() == KeyCode.DOWN && direction != MoveDirections.UP) {
                direction = MoveDirections.DOWN;
            }
            if (key.getCode() == KeyCode.RIGHT && direction != MoveDirections.LEFT) {
                direction = MoveDirections.RIGHT;
            }
        });
        animate();

    }

    private void animate(){
        frame = new KeyFrame(Duration.millis(500), event -> {
            if (gameOver) {
                animation.stop();
                try {
                    showGameOverDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            snakeMove();
        });
        animation = new Timeline();
        animation.getKeyFrames().add(frame);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        timeFrame = new KeyFrame(Duration.millis(1000), event -> {
            if (gameOver){
                timeAnimation.stop();
                return;
            }

            time++;
            String min, sec;

            if (time / 60 < 10)
                min = "0" + time / 60;
            else
                min = time / 60 + "";

            if (time % 60 < 10)
                sec = "0" + time % 60;
            else
                sec = time % 60 + "";

            timeBoard.setText("TIME: " + min + ":" + sec);
        });
        timeAnimation = new Timeline();
        timeAnimation.getKeyFrames().add(timeFrame);
        timeAnimation.setCycleCount(Animation.INDEFINITE);
        timeAnimation.play();
    }

    void setColsAndRows(int rows, int cols){
        this.rows = rows;
        this.cols = cols;


        for (int i=0; i<rows; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(50);
            board.getRowConstraints().add(rowConst);
        }

        for (int i=0; i<cols; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(50);
            board.getColumnConstraints().add(colConst);
        }


        board.setAlignment(Pos.CENTER);
        board.setPrefSize(cols*50, rows*50);
        board.setMaxSize(cols*50, rows*50);
        board.getStyleClass().add("board");
        board.setGridLinesVisible(true);
    }

    private void showGameOverDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/res/fxml/game_over_dialog.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStylesheets().add("/sample/res/css/game_over_dialog.css");

        GameOverDialogController controller = fxmlLoader.getController();
        controller.initValues(time, score, cols, rows);

        board.add(parent, cols/2-5, rows/2-3, cols, 6);

    }

    private void snakeMove(){
        switch (direction){
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    private void moveUp(){
        if (snakeBody.get(0).getRow()-1<0) { // JEZELI WAZ WYJDZIE POZA PLANSZE
            gameOver = true;
        }
        else { // RUCH WEZEM
            moveElements(snakeBody.get(0).getRow() - 1, MoveDirections.UP);
            snakeBitItself();

            int  bodyRow;
            if (snakeBody.get(snakeBody.size() - 1).getRow() + 1 > rows-1)
                bodyRow = snakeBody.get(snakeBody.size() - 1).getRow() - 1;
            else
                bodyRow = snakeBody.get(snakeBody.size() - 1).getRow() + 1;

            snakeAteGoodFood(snakeBody.get(snakeBody.size() - 1).getCol(), bodyRow);
            snakeAteBadFood();
            boardFullOfSnake();
        }
    }

    private void moveDown(){
        if (snakeBody.get(0).getRow()+1>rows-1) // JEZELI WAZ WYJDZIE POZA PLANSZE
            gameOver=true;
        else { // RUCH WEZEM
            moveElements(snakeBody.get(0).getRow() + 1, MoveDirections.DOWN);
            snakeBitItself();

            int  bodyRow;
            if (snakeBody.get(snakeBody.size() - 1).getRow() - 1 < 0)
                bodyRow = snakeBody.get(snakeBody.size() - 1).getRow() + 1;
            else
                bodyRow = snakeBody.get(snakeBody.size() - 1).getRow() - 1;

            snakeAteGoodFood(snakeBody.get(snakeBody.size() - 1).getCol(), bodyRow);
            snakeAteBadFood();
            boardFullOfSnake();
        }
    }

    private void moveLeft(){
        if (snakeBody.get(0).getCol()-1<0) // JEZELI WAZ WYJDZIE POZA PLANSZE
            gameOver=true;
        else { // RUCH WEZEM
            moveElements(snakeBody.get(0).getCol() - 1, MoveDirections.LEFT);
            snakeBitItself();

            int  bodyCol;
            if (snakeBody.get(snakeBody.size() - 1).getCol() + 1 > cols-1)
                bodyCol = snakeBody.get(snakeBody.size() - 1).getCol() - 1;
            else
                bodyCol = snakeBody.get(snakeBody.size() - 1).getCol() + 1;

            snakeAteGoodFood(bodyCol, snakeBody.get(snakeBody.size() - 1).getRow());
            snakeAteBadFood();
            boardFullOfSnake();
        }
    }

    private void moveRight(){
        if (snakeBody.get(0).getCol()+1>cols-1) { // JEZELI WAZ WYJDZIE POZA PLANSZE
            gameOver = true;
        }
        else { // RUCH WEZEM
            moveElements(snakeBody.get(0).getCol() + 1, MoveDirections.RIGHT);
            snakeBitItself();

            int  bodyCol;
            if (snakeBody.get(snakeBody.size() - 1).getCol() - 1 < 0)
                bodyCol = snakeBody.get(snakeBody.size() - 1).getCol() + 1;
            else
                bodyCol = snakeBody.get(snakeBody.size() - 1).getCol() - 1;

            snakeAteGoodFood(bodyCol, snakeBody.get(snakeBody.size() - 1).getRow());
            snakeAteBadFood();
            boardFullOfSnake();
        }
    }

    private void snakeBitItself() {
        if (snakeBody.size() > 1) { // JEZELI WAZ UGRZYZIE SAMEGO SIEBIE
            for (int i = 1; i < snakeBody.size(); i++) {
                if (snakeBody.get(0).getRow() == snakeBody.get(i).getRow() &&
                        snakeBody.get(0).getCol() == snakeBody.get(i).getCol()) {
                    gameOver = true;
                    break;
                }
            }
        }
    }

    private void snakeAteGoodFood(int newBodyCol, int newBodyRow) {
        if (snakeBody.get(0).getRow()==goodFood.getRow() && snakeBody.get(0).getCol() == goodFood.getCol()){

            SnakeBodyElement newElement = new SnakeBodyElement(50, 50,
                    newBodyCol, newBodyRow);

            newElement.getStyleClass().add("snakeBody");

            snakeBody.add(newElement);
            score++;
            scoreBoard.setText("SCORE: " + score);
            for (SnakeBodyElement snakeBodyElement : snakeBody) {
                board.getChildren().remove(snakeBodyElement);
                board.add(snakeBodyElement, snakeBodyElement.getCol(), snakeBodyElement.getRow());
            }
            board.getChildren().remove(goodFood);
            goodFood = new Food(30, 30, new Random().nextInt(cols), new Random().nextInt(rows), FoodType.GOOD);
            goodFood.getStyleClass().add("goodFood");
            board.add(goodFood, goodFood.getCol(), goodFood.getRow());
            GridPane.setHalignment(goodFood, HPos.CENTER);
            if (Food.foodCounter%10==0) {
                badFood = new Food(30, 30, new Random().nextInt(cols), new Random().nextInt(rows), FoodType.BAD);
                badFood.getStyleClass().add("badFood");
                board.add(badFood, badFood.getCol(), badFood.getRow());
                GridPane.setHalignment(badFood, HPos.CENTER);
            }
        }
    }

    private void boardFullOfSnake(){
        if (snakeBody.size()==(cols*rows))
            gameOver=true;
    }

    private void snakeAteBadFood() {
        if (badFood!=null) {
            if (snakeBody.get(0).getRow() == badFood.getRow() && snakeBody.get(0).getCol() == badFood.getCol()){
                if (snakeBody.size()>1) {
                    board.getChildren().remove(snakeBody.get(snakeBody.size()-1));
                    snakeBody.remove(snakeBody.size() - 1);
                    board.getChildren().remove(badFood);
                }
                score++;
                scoreBoard.setText("SCORE: " + score);
            }
        }
    }

    private void moveElements(int i2, MoveDirections direction) {
        for (int i=snakeBody.size()-1; i>=1; i--){
            snakeBody.get(i).setRow(snakeBody.get(i-1).getRow());
            snakeBody.get(i).setCol(snakeBody.get(i-1).getCol());
        }
        if (direction == MoveDirections.RIGHT || direction == MoveDirections.LEFT)
            snakeBody.get(0).setCol(i2);
        else
            snakeBody.get(0).setRow(i2);
        for (SnakeBodyElement snakeBodyElement : snakeBody) {
            board.getChildren().remove(snakeBodyElement);
            board.add(snakeBodyElement, snakeBodyElement.getCol(), snakeBodyElement.getRow());
        }
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
        Parent playWindow = FXMLLoader.load(getClass().getResource("/sample/res/fxml/start_page.fxml"));
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/start_page.css").toExternalForm());

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        animation.stop();
        timeAnimation.stop();
        mainStage.show();
    }

}

