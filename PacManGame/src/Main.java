import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuWindow menuWindow = new MenuWindow();
            menuWindow.setLocationRelativeTo(null);
            menuWindow.setVisible(true);
        });
    }
}


