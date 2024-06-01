import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MenuWindow extends JFrame {
    private GameBoard gameBoard;
    private int rowQty;
    private int colQty;

    public MenuWindow() throws HeadlessException {


        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/pacmanLogo.png")));
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading image.");
        }
        this.add(new JLabel(icon));
        this.setTitle("Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);

        JButton b1 = new JButton("New Game");
        JButton b2 = new JButton("High Scores");
        JButton b3 = new JButton("Exit");

        b1.setFont(new Font("Arial", Font.BOLD, 26));
        b2.setFont(new Font("Arial", Font.BOLD, 26));
        b3.setFont(new Font("Arial", Font.BOLD, 26));

        b1.setBackground(Color.BLACK);
        b2.setBackground(Color.BLACK);
        b3.setBackground(Color.BLACK);

        b1.setForeground(Color.YELLOW);
        b2.setForeground(Color.YELLOW);
        b3.setForeground(Color.YELLOW);

        b1.setToolTipText("Start a new game");
        b2.setToolTipText("View highest scores");
        b3.setToolTipText("Exit this window");

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);

        this.add(buttonPanel, BorderLayout.NORTH);

        this.setVisible(true);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBoardSizeDialog();

                if(getColQty() >= 10 && getColQty() <= 100 && getRowQty() >= 10 && getRowQty() <= 100){
                    closeWindow();
                    //createGameWindow
                    GameRunThread gameRunThread = new GameRunThread(getColQty(), getRowQty());
                    SwingUtilities.invokeLater(gameRunThread);

                } else{
                    System.exit(0);
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighScoreWindow highScoreWindow = new HighScoreWindow();
            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void showBoardSizeDialog(){

            JPanel panel = new JPanel(new GridLayout(0, 2));

            SpinnerModel modelRows = new SpinnerNumberModel(10, 10, 100, 1);
            JSpinner spinnerRows = new JSpinner(modelRows);

            panel.add(new JLabel("Select Board Size"));
            panel.add(spinnerRows, BorderLayout.WEST);

            SpinnerModel modelCols = new SpinnerNumberModel(10, 10, 100, 1);
            JSpinner spinnerCols = new JSpinner(modelCols);

            panel.add(new JLabel("Select Board Size"));
            panel.add(spinnerCols, BorderLayout.EAST);

            int resultRow = JOptionPane.showConfirmDialog(panel, spinnerRows, "Board Row Size", JOptionPane.OK_CANCEL_OPTION);
            if (resultRow == JOptionPane.OK_OPTION) {
                setRowQty((Integer) spinnerRows.getValue());
            }

            int resultCol = JOptionPane.showConfirmDialog(panel, spinnerCols, "Board Column Size", JOptionPane.OK_CANCEL_OPTION);
            if (resultCol == JOptionPane.OK_OPTION) {
                setColQty((Integer) spinnerCols.getValue());
            }

    }



    public int getRowQty() {
        return rowQty;
    }

    public void setRowQty(int rowQty) {
        this.rowQty = rowQty;
    }

    public int getColQty() {
        return colQty;
    }

    public void setColQty(int colQty) {
        this.colQty = colQty;
    }

    public void closeWindow() {
        dispose();
    }
}
