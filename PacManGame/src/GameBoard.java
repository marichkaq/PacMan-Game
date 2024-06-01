import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameBoard extends JTable {

    private final GameWindow gameWindow;
    private final BoardTableCellRenderer tableCellRenderer;
    private final Game game;
    private BoardTableModel boardTableModel;


    public GameBoard(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        game = gameWindow.getGame();
        boardTableModel = new BoardTableModel(game);
        tableCellRenderer = new BoardTableCellRenderer(game);
        this.setModel(boardTableModel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResizing();
            }
        });

        for(int i = 0; i < game.getX(); i++){
            this.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }
    }

    protected void handleResizing(){
        Dimension size = this.getSize();

        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        int cellSize = Math.min(size.width/ columnCount, size.height/ rowCount);
        this.setRowHeight(cellSize);
        tableCellRenderer.setCellSize(cellSize, cellSize);
        for(int i = 0; i < columnCount; i++){
            this.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Player player = game.getPlayer();
        player.getPlayerAnimation().render(this, g);

        for(Ghost ghost : game.getGhosts()){
            ghost.render(this, g);
        }

        UpgradeManager upgradeManager = game.getUpgradeManager();
        upgradeManager.render(this, g);


    }


}
