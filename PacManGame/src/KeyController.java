import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {
    private Game game;

    public KeyController(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Player player = game.getPlayer();
        switch(keyCode){
            case KeyEvent.VK_RIGHT -> player.setNewDirection(Game.Direction.RIGHT);
            case KeyEvent.VK_LEFT -> player.setNewDirection(Game.Direction.LEFT);
            case KeyEvent.VK_UP -> player.setNewDirection(Game.Direction.UP);
            case KeyEvent.VK_DOWN -> player.setNewDirection(Game.Direction.DOWN);
        }
        int modifiers = e.getModifiersEx();
        if(keyCode == KeyEvent.VK_Q && (modifiers & KeyEvent.CTRL_DOWN_MASK) != 0 && (modifiers & KeyEvent.SHIFT_DOWN_MASK) != 0){
            game.gameEnd();
            System.out.println("Exit shortcut entered.");
        }


    }
}
