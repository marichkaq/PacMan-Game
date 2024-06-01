public class DoublePointsUpgrade extends Upgrade {
    public DoublePointsUpgrade(Game game) {
        super(game);

    }

    @Override
    void activateUpgrade() {
        double originalFactor = game.getScoreFactor();
        game.setScoreFactor(2);
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            return;
        }
        game.setScoreFactor(originalFactor);
    }

    @Override
    Game.FoodType getFoodType() {
        return Game.FoodType.CHERRY;
    }
}
