import java.io.Serializable;

public class HighScoreEntry implements Serializable, Comparable<HighScoreEntry> {
    private String player;
    private int score;

    public HighScoreEntry(String player, int score) {
        this.player = player;
        this.score = score;
    }

    @Override
    public int compareTo(HighScoreEntry o) {
        return Integer.compare(o.score, this.score);

    }

    @Override
    public String toString() {

        return this.player + "  " + this.score;
    }
}
