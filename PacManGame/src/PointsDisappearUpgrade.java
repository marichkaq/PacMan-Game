public class PointsDisappearUpgrade extends Upgrade {
    public PointsDisappearUpgrade(Game game) {
        super(game);
    }

    @Override
    void activateUpgrade() {
        game.setPointsInvisible(true);
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            return;
        }
        game.setPointsInvisible(false);
    }

    @Override
    Game.FoodType getFoodType() {
        return Game.FoodType.LIME;
    }
}
