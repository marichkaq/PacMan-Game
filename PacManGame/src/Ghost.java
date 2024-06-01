import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Ghost {
    private final Game game;
    private Position position;
    private ArrayList<ImageIcon> rawImages;
    private ArrayList<ImageIcon> renderedImages;
    private int imageIndex;
    private int imageWidth;
    private int imageHeight;
    final GhostMoveThread moveThread;

    public Ghost(Game game) {
        this.game = game;
        this.position = game.getNotWallPos();
        rawImages = new ArrayList<>();
        rawImages.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/blinky.png"))));
        rawImages.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/clyde.png"))));
        rawImages.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/inky.png"))));
        rawImages.add(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/pinky.png"))));
        imageIndex = 0;
        moveThread = new GhostMoveThread(this);
        moveThread.start();
    }

    public Position getPosition(){
        return position;
    }

    public int getPosX(){
        return position.getX();
    }

    public int getPosY(){
        return position.getY();
    }

    public void setPosition(Position position){
        this.position = position;
            game.handlePositionChange();
    }

    synchronized public void render(JTable table, Graphics g){


        int x = getPosX();
        int y = getPosY();

        Rectangle cellRect = table.getCellRect(y, x, true);
        int cellWidth = cellRect.width;
        int cellHeight = cellRect.height;

        if (imageWidth != cellWidth || imageHeight != cellHeight || renderedImages == null) {
            renderedImages = new ArrayList<>();
            for (ImageIcon icon : rawImages) {
                Image rawImage = icon.getImage();
                Image renderedImage = rawImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
                renderedImages.add(new ImageIcon(renderedImage));
            }
            imageWidth = cellWidth;
            imageHeight = cellHeight;
        }

        if (!renderedImages.isEmpty()) {
            Random random = new Random();
            int randomImage = random.nextInt(renderedImages.size());
            ImageIcon imageIcon = renderedImages.get(randomImage);

            int renderX = cellRect.x;
            int renderY = cellRect.y;

            imageIcon.paintIcon(table, g, renderX, renderY);
        }

    }

    public Game getGame() {
        return game;
    }

    public void stop() {
        //interrupt move thread
        moveThread.interrupt();
    }
}
