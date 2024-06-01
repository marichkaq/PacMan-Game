import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreWindow extends JFrame {
    private JList<String> highScores;
    private ArrayList<HighScoreEntry> highScoreEntries;

    public HighScoreWindow()  {
        HighScore highScore = new HighScore();
        this.highScoreEntries = highScore.getHighScoreList();
        DefaultListModel<String> lModel = new DefaultListModel<>();
        for(HighScoreEntry highScoreEntry : highScoreEntries){
            lModel.addElement(highScoreEntry.toString());
        }
        highScores = new JList<>(lModel);
        highScores.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setText(value.toString());
                return label;
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(highScores), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("High Scores");
        setSize(250, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
