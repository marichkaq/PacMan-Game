import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BoardTableCellRenderer extends DefaultTableCellRenderer {
    private int cellWidth;
    private int cellHeight;
    private static Map<Game.BoardEl, ImageIcon> elementImage = new HashMap<>();
    private Game game;
    private ArrayList<Upgrade> upgrades;

    public BoardTableCellRenderer(Game game) {
        this.game = game;
        setCellSize(20, 20);


    }

    public void setCellSize(int width, int height){
        this.cellWidth = width;
        this.cellHeight = height;
        elementImage.clear();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if(value != null){
            Game.BoardEl el = game.gameBoard[row][column];
            if(game.arePointsInvisible() && el == Game.BoardEl.POINT) {
                setIcon(null);
            } else {
                ImageIcon imageIcon = getImg(el);
                setIcon(imageIcon);
            }
        }
        return this;
    }

    private ImageIcon getImg(Game.BoardEl el){
        ImageIcon imageIcon = elementImage.get(el);
        if(imageIcon != null){
            return imageIcon;
        }

        imageIcon = loadImageIcon(el);

        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        elementImage.put(el, imageIcon);
        return imageIcon;
    }

    private ImageIcon loadImageIcon(Game.BoardEl el){
        String file = null;
        switch (el){
            case POINT: file = "/pacman_images/dot.png";
                break;
            case WALL:
                file = "/pacman_images/block.png";
                break;
            case FOOD:
                if(el.getFoodType() != null){
                    if(el.getFoodType() == Game.FoodType.APPLE){
                        file = "/pacman_images/apple.png";
                        break;
                    } else if (el.getFoodType() == Game.FoodType.CHERRY) {
                        file = "/pacman_images/cherry.png";
                        break;
                    } else if (el.getFoodType() == Game.FoodType.LIME) {
                        file = "/pacman_images/lime.png";
                        break;
                    } else if (el.getFoodType() == Game.FoodType.ORANGE) {
                        file = "/pacman_images/orange.png";
                        break;
                    } else if (el.getFoodType() == Game.FoodType.STRAWBERRY) {
                        file = "/pacman_images/strawberry.png";
                        break;
                    }
                }
                break;
            default:
                file =  "/pacman_images/defaultBlock.png";
                break;
        }

            return new ImageIcon(Objects.requireNonNull(getClass().getResource(file)));

    }
}


