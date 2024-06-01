import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameWindow extends JFrame {
    private final Game game;
    private final GameBoard board;
    private final JLabel timeLabel;
    private final JPanel livesPanel;
    private final JLabel scoreLabel;
    private final JLabel upgradeLabel;
    private final List<ImageIcon> heartIcons;
    private String playerName;
    private final GameTimeThread timeThread;
    private final RepaintThread repaintThread;


    public GameWindow(Game game) throws HeadlessException {
        this.game = game;
        JPanel topPanel = new JPanel(); //time    score
        JPanel bottomPanel = new JPanel(); //lives          upgrade
        JPanel boardPanel = new JPanel(new BorderLayout());
        timeLabel = new JLabel();
        scoreLabel = new JLabel();
        livesPanel = new JPanel();
        upgradeLabel = new JLabel();

        heartIcons = new ArrayList<>();

        ImageIcon livesIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/heart.png")));
        if (livesIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading image.");
        }

        livesIcon.setImage(livesIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        for (int i = 0; i < game.getLives(); i++) {
            heartIcons.add(livesIcon);
        }


        topPanel.add(timeLabel);
        topPanel.add(scoreLabel);
        bottomPanel.add(livesPanel);
        bottomPanel.add(upgradeLabel);

        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);

        board = new GameBoard(this);
        boardPanel.add(board, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.CENTER);

        board.addKeyListener(new KeyController(game));

        setSize(500, 500);
        setLocationRelativeTo(null);

        timeThread = new GameTimeThread(timeLabel);
        timeThread.start();
        repaintThread = new RepaintThread(this);
        repaintThread.start();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.stop();
                showGameOverDialog();
            }
        });
        //getContentPane().setBackground(Color.BLACK);

        updateScore();
        updateLives();

        if(game.getY() > 20 || game.getX() > 20){
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        setVisible(true);
    }

    public Game getGame() {
        return game;
    }

    public void updateScore() {
        scoreLabel.setText("Score: " + game.getPlayerScore());
    }

    public void updateLives(){
        livesPanel.removeAll();
        int livesNum = game.getLives();

        for (int i = 0; i < livesNum; i++) {

            livesPanel.add(new JLabel(heartIcons.get(i)));
        }
        revalidate();
        repaint();
    }

    public void setUpgradeLabelText(String s){
        upgradeLabel.setText(s);
    }

    public void showWinningDialog() {
        ImageIcon winIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/win.png")));
        if (winIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading image.");
        }

        String message = "Congratulations! You won the game!";
        JOptionPane.showMessageDialog(this, message, "You win!", JOptionPane.INFORMATION_MESSAGE, winIcon);
    }

    public void showGameOverDialog(){
        ImageIcon gameOverIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/gameOver.png")));
        if (gameOverIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading image.");
        }

        String message =  "Game Over!\nYour Score: " + game.getPlayerScore() + "\nWould you like to save your score?";
        Object[] options = {"Yes!", "No, thanks"};
        int choice = JOptionPane.showOptionDialog(this, message, "Game over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,gameOverIcon, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            // handle saving the score
            playerName =JOptionPane.showInputDialog(this, "Enter your name:");
            HighScore highScore = new HighScore();
            highScore.addHighScore(this.playerName, game.getPlayerScore());
            dispose();
            new MenuWindow();
        } else {
            dispose();
            new MenuWindow();        }
    }

    public GameBoard getBoard() {
        return board;
    }

    public void stop() {
        //interrupt GameTimeThread
        //interrupt RepaintThread
        timeThread.interrupt();
        repaintThread.interrupt();
    }
}
