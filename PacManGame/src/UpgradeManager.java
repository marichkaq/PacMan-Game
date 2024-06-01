import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpgradeManager {
    private Game game;
    private boolean isActive;
    private Position position;
    private Map<Game.FoodType, ImageIcon> upgradeIcons;
    private Upgrade currentUpgrade;
    private ImageIcon currentIcon;
    GenerateUpgradeThread generateUpgradeThread;
    public UpgradeManager(Game game) {
        this.game = game;
        isActive = false;

        upgradeIcons = new HashMap<>();

        upgradeIcons.put(Game.FoodType.APPLE, new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/apple.png"))));
        upgradeIcons.put(Game.FoodType.CHERRY, new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/cherry.png"))));
        upgradeIcons.put(Game.FoodType.LIME, new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/lime.png"))));
        upgradeIcons.put(Game.FoodType.ORANGE, new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/orange.png"))));
        upgradeIcons.put(Game.FoodType.STRAWBERRY, new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacman_images/strawberry.png"))));

        generateUpgradeThread = new GenerateUpgradeThread(this);
        generateUpgradeThread.start();
    }

    private Upgrade generateUpgrade(){
        Game.FoodType foodType = Game.FoodType.getRandomFood();
        currentUpgrade = switch (foodType){
            case APPLE -> new InvisibilityUpgrade(game);
            case CHERRY -> new DoublePointsUpgrade(game);
            case ORANGE -> new RandomTeleportUpgrade(game);
            case LIME -> new PointsDisappearUpgrade(game);
            case STRAWBERRY -> new InvulnerabilityUpgrade(game);
        };
        return currentUpgrade;
    }

    public void setActive(){
        position = game.getRandomGhostPos();
        isActive = true;
        currentUpgrade = generateUpgrade();
        currentIcon = upgradeIcons.get(currentUpgrade.getFoodType());

    }

    public void setCollected(){
        isActive = false;
        if(currentUpgrade != null){
            Thread thread = new Thread(currentUpgrade);
            thread.start();
        }
    }

    public void render(JTable table, Graphics g){
        if(!isActive()) {
            return;
        }
        if (currentIcon != null) {
            int x = position.getX();
            int y = position.getY();

            Rectangle cellRect = table.getCellRect(y, x, true);
            int cellWidth = cellRect.width;
            int cellHeight = cellRect.height;

            Image image = currentIcon.getImage();
            Image scaledImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            int renderX = cellRect.x;
            int renderY = cellRect.y;

            scaledIcon.paintIcon(table, g, renderX, renderY);
        }
    }
    public void stop() {
        //interruptThread
        generateUpgradeThread.interrupt();
    }


    public boolean isActive() {
        return isActive;
    }

    public Position getPosition() {
        return position;
    }

}
