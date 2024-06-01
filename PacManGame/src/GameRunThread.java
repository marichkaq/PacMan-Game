import javax.swing.*;

public class GameRunThread extends Thread{
    private final int x;
    private final int y;
    private Game game;

    public GameRunThread(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            try {
                game = new Game(x, y);
                game.setGameWindow(new GameWindow(game));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
