public class RandomTeleportUpgrade extends Upgrade {
    public RandomTeleportUpgrade(Game game) {
        super(game);
    }

    @Override
    void activateUpgrade() {
        Position randomPos = game.getNotWallPos();
        game.getPlayer().setPosition(randomPos);
    }

    @Override
    Game.FoodType getFoodType() {
        return Game.FoodType.ORANGE;
    }
}
