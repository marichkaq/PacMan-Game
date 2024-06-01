public abstract class Upgrade implements Runnable {
    protected Game game;
    protected String text;

    Upgrade(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        game.getGameWindow().setUpgradeLabelText(text);
        activateUpgrade();
        game.getGameWindow().setUpgradeLabelText("");
    }

    abstract void activateUpgrade();

    abstract Game.FoodType getFoodType();
}
