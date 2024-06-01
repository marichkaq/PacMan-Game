import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore implements Serializable {
    private ArrayList<HighScoreEntry> highScoreList;


    public HighScore() {
        highScoreList = loadScoresFromFile();
        Collections.sort(highScoreList);
    }


    public ArrayList<HighScoreEntry> getHighScoreList() {
        return highScoreList;
    }

    protected void addHighScore(String name, int score){
        highScoreList.add(new HighScoreEntry(name, score));
        updateHighScores();
        Collections.sort(highScoreList);
    }

    private ArrayList<HighScoreEntry> loadScoresFromFile() {
        ArrayList<HighScoreEntry> scoresFromFile = new ArrayList<>();
        File f = new File("HighScores.txt");
        if(!f.exists())
            return scoresFromFile;
        try{
            FileInputStream fInputStream = new FileInputStream("HighScores.txt");
            ObjectInputStream objInputStream = new ObjectInputStream(fInputStream);
            scoresFromFile = (ArrayList<HighScoreEntry>) objInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } return scoresFromFile;
    }

    private void updateHighScores(){
        String file = "HighScores.txt";
        try{
            FileOutputStream fOutputStream =  new FileOutputStream(file); //
            ObjectOutputStream  objOutputStream = new ObjectOutputStream(fOutputStream);       //
            objOutputStream.writeObject(highScoreList);
            objOutputStream.close();
            fOutputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
