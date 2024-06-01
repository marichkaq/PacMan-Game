public class InvisibilityUpgrade extends Upgrade {
    private Player player;
    public InvisibilityUpgrade(Game game) {
        super(game);
        player = game.getPlayer();
    }

    @Override
    void activateUpgrade() {
        player.setInvisible(true);
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            return;
        }
        player.setInvisible(false);
    }

    @Override
    Game.FoodType getFoodType() {
        return Game.FoodType.APPLE;
    }
}
