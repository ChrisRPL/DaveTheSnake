package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.models.Score;
import sample.ScoreCellFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoresPageController {

    private final String FILENAME = "high_scores.ser";
    private ArrayList<Score> highScores;
    private FileInputStream fileIn = null;
    private ObjectInputStream objectIn = null;

    @FXML
    private ListView<Score> scoresList;

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
    void initialize() {

        File f = new File(FILENAME);
        if (f.exists()) {
            try {
                fileIn = new FileInputStream(FILENAME);
                objectIn = new ObjectInputStream(fileIn);
                highScores = (ArrayList<Score>) objectIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            scoresList.setCellFactory(new ScoreCellFactory());
            Collections.sort(highScores);

            for (Score score : highScores) {
                scoresList.getItems().add(score);
                System.out.println(score.getGameVal());
            }
        }
    }

}

