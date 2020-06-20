package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


import java.io.IOException;

public class PlayPageController {

    @FXML
    private ComboBox<Integer> colsAndRows;

    @FXML
    private ComboBox<Integer> cols;

    @FXML
    private ComboBox<Integer> rows;

    @FXML
    void initialize() {
        for (int i = 8; i <= 15; i++) {
            colsAndRows.getItems().add(i);
            cols.getItems().add(i);
            rows.getItems().add(i);
        }

        colsAndRows.setDisable(true);
        cols.setDisable(true);
        rows.setDisable(true);
        cols.setValue(8);
        rows.setValue(8);
        colsAndRows.setValue(8);
    }

    @FXML
    void setColsAndRows() {
        colsAndRows.setDisable(false);
        cols.setDisable(true);
        rows.setDisable(true);
        cols.setValue(8);
        rows.setValue(8);
    }

    @FXML
    void setCols() {
        cols.setDisable(false);
        colsAndRows.setDisable(true);
        colsAndRows.setValue(8);
        rows.setDisable(true);
    }

    @FXML
    void setRows() {
        rows.setDisable(false);
        colsAndRows.setDisable(true);
        colsAndRows.setValue(8);
        cols.setDisable(true);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent playWindow = FXMLLoader.load(getClass().getResource("/sample/res/fxml/start_page.fxml"));
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/start_page.css").toExternalForm());

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        mainStage.show();
    }

    @FXML
    void playGame(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/res/fxml/game_page.fxml"));
        Parent playWindow = loader.load();
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/game_page.css").toExternalForm());

        GamePageController gamePageController = loader.getController();

        int cols, rows;
        if (colsAndRows.getValue()!=8){
            cols = colsAndRows.getValue();
            rows = colsAndRows.getValue();
        } else {
            cols = this.cols.getValue();
            rows = this.rows.getValue();
        }

        gamePageController.setColsAndRows(rows, cols);

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        mainStage.show();
    }


}
