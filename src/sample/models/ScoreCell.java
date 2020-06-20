package sample.models;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ScoreCell extends ListCell<Score> {

    @FXML
    private Label username;

    @FXML
    private Label score;

    @FXML
    private Label time;

    @FXML
    private Label board;

    public ScoreCell() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/res/fxml/score_cell.fxml"));
        loader.setController(this);
        this.getStylesheets().add(getClass().getResource("/sample/res/css/score_cell.css").toExternalForm());
        loader.setRoot(this);
        loader.load();
    }

    @Override
    protected void updateItem(Score item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty){
            String min = "", sec;

            if (item.getTime() / 60 < 10)
                min = "0" + item.getTime() / 60;
            else
                min = item.getTime() / 60 + "";

            if (item.getTime() % 60 < 10)
                sec = "0" + item.getTime() % 60;
            else
                sec = item.getTime() % 60 + "";

            username.setText(item.getUsername());
            score.setText("SCORE: " + item.getScore());
            time.setText("TIME: " + min + ":" + sec);
            board.setText("BOARD: " + item.getCols() + "x" + item.getRows());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
}
