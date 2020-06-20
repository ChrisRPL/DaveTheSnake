package sample.models;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {

    private String username;
    private int time, score, cols, rows, gameVal;

    public Score(String username, int time, int score, int cols, int rows) {
        this.username = username;
        this.time = time;
        this.score = score;
        this.cols = cols;
        this.rows = rows;

        this.gameVal = score*10 - time/10 - cols - rows;
    }


    @Override
    public int compareTo(Score o) {
        if (this.gameVal > o.gameVal)
            return -1;
        else if (this.gameVal < o.gameVal)
            return 1;
        else
            return this.username.compareTo(o.username);
    }

    public String getUsername() {
        return username;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int getGameVal() {
        return gameVal;
    }
}
