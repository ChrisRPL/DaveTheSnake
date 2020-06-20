package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class StartPageController {

    @FXML
    private Button exitBtn;

    @FXML
    void showPlayPage(ActionEvent event) throws IOException {
        Parent playWindow = FXMLLoader.load(getClass().getResource("/sample/res/fxml/play_page.fxml"));
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/play_page.css").toExternalForm());

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        mainStage.show();
    }

    @FXML
    void showHighScores(ActionEvent event) throws IOException {
        Parent playWindow = FXMLLoader.load(getClass().getResource("/sample/res/fxml/high_scores.fxml"));
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/high_scores.css").toExternalForm());

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        mainStage.show();
    }

    @FXML
    void exitApp() {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}
