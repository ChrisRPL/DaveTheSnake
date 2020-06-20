package sample;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import sample.models.Score;
import sample.models.ScoreCell;

import java.io.IOException;


public class ScoreCellFactory implements Callback<ListView<Score>, ListCell<Score>> {

    @Override
    public ListCell<Score> call(ListView<Score> param) {
        try {
            return new ScoreCell();
        } catch (IOException e) {
            return null;
        }
    }
}
