import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PlayerAnimation {
    private Player player;
    private GameWindow gameWindow;
    private ImageIcon mouseOpen;
    private ImageIcon mouseClosed;
    private ImageIcon mouseOpenRendered;
    private ImageIcon mouseClosedRendered;
    private boolean isOpen;
    private final PlayerAnimationThread playerAnimationThread;
    int imageWidth;
    int imageHeight;
    Game.Direction direction;

    public PlayerAnimation(Player player) {
        this.player = player;
        this.gameWindow = player.getGame().getGameWindow();
        mouseClosed = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/pacmanMouseClosed.png")));
        mouseOpen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/pacmanMouseOpen.png")));
        isOpen = true;
        direction = Game.Direction.STILL;
        playerAnimationThread = new PlayerAnimationThread(this);
        playerAnimationThread.start();
    }

    private Image turnImage(Image img, Game.Direction dir){
        if(dir == Game.Direction.RIGHT){
            return img;
        }
        int turnAngle = 0;

        switch (dir){
            case LEFT -> turnAngle = 180;
            case UP -> turnAngle = 270;
            case DOWN -> turnAngle = 90;
        }

        int width = img.getWidth(null);
        int height = img.getHeight(null);

        BufferedImage turnedImg = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(turnAngle), height/2, height/2);
        Graphics2D graphics2D = turnedImg.createGraphics();
        graphics2D.drawImage(img, affineTransform, null);
        graphics2D.dispose();
        return turnedImg;
    }

    synchronized public void render(JTable table, Graphics g){
        if(!player.isInvisible()) {
            Rectangle cell = table.getCellRect(player.getPosY(), player.getPosX(), false);
            if (this.imageWidth != cell.width || this.imageHeight != cell.height || player.getDirection() != this.direction
                    || mouseOpenRendered == null) {
                Image openImg = mouseOpen.getImage();
                openImg = turnImage(openImg, player.getDirection());
                Image scaledOpenImg = openImg.getScaledInstance(cell.width, cell.height, Image.SCALE_SMOOTH);
                mouseOpenRendered = new ImageIcon(scaledOpenImg);
                Image closeImg = mouseClosed.getImage();
                closeImg = turnImage(closeImg, player.getDirection());
                Image scaledCloseImg = closeImg.getScaledInstance(cell.width, cell.height, Image.SCALE_SMOOTH);
                mouseClosedRendered = new ImageIcon(scaledCloseImg);

            }
            int x = cell.x;
            int y = cell.y;

            final Position movePos = player.getMovePosition();
            x += cell.width * movePos.getX() / player.moveSteps;
            y += cell.height * movePos.getY() / player.moveSteps;

            getRenderedIcon().paintIcon(table, g, x, y);
        }
    }

    private ImageIcon getRenderedIcon() {
        if (isOpen) {
            return mouseOpenRendered;
        } else {
            return mouseClosedRendered;
        }

    }


    public void swapIcon() {
        isOpen = !isOpen;
    }

    public void stop(){
        playerAnimationThread.interrupt();
    }
}
