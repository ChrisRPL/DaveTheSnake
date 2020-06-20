package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Score;

import java.io.*;
import java.util.ArrayList;

public class GameOverDialogController {

    private final String FILENAME = "high_scores.ser";
    private int time, score, cols, rows;

    @FXML
    public TextField confirmTxt;

    @FXML
    void confirmAndFinish(ActionEvent event) throws IOException {
        Score score = new Score(confirmTxt.getText(), time, this.score, cols, rows);

        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;

        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        File f = new File(FILENAME);
        ArrayList<Score> highScores = new ArrayList<>();
        try {
            if (f.exists()) {
                fileIn = new FileInputStream(FILENAME);
                objectIn = new ObjectInputStream(fileIn);

                highScores = (ArrayList<Score>) objectIn.readObject();
                highScores.add(score);


                fileIn.close();
                objectIn.close();

                fileOut = new FileOutputStream(FILENAME);
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(highScores);

                objectOut.close();
                fileOut.close();
            }else {
                fileOut = new FileOutputStream(FILENAME);
                objectOut = new ObjectOutputStream(fileOut);
                highScores.add(score);
                objectOut.writeObject(highScores);

                objectOut.close();
                fileOut.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Parent playWindow = FXMLLoader.load(getClass().getResource("/sample/res/fxml/start_page.fxml"));
        Scene playScene = new Scene(playWindow);
        playScene.getStylesheets().add(getClass().getResource("/sample/res/css/start_page.css").toExternalForm());

        Stage mainStage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        mainStage.setScene(playScene);
        mainStage.show();

    }

    void initValues(int time, int score, int cols, int rows) {
        this.time = time;
        this.score = score;
        this.cols = cols;
        this.rows = rows;
    }

}

