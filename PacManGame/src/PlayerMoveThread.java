public class PlayerMoveThread extends Thread{
    private Player player;

    public PlayerMoveThread(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        final Game game = player.getGame();
        while (!Thread.interrupted()){
            synchronized (this){
                if(player.getNewDirection() != Game.Direction.STILL){
                    if(game.isDirectionValid(player.getPosition(), player.getNewDirection())){
                        player.setDirection(player.getNewDirection());
                        player.setNewDirection(Game.Direction.STILL);
                    }
                }
                int speed = player.getSpeed();
                Game.Direction dir = player.getDirection();
                boolean canMoveTo = game.isDirectionValid(player.getPosition(), dir);
                Position nextPosition = game.getNewPositionInDirection(player.getPosition(), dir);
                try{
                    if(dir == Game.Direction.STILL || !canMoveTo){
                        Thread.sleep(speed);
                        continue;
                    } else{
                        for(int i = 0; i < player.moveSteps; i++){
                            Thread.sleep(speed / player.moveSteps);
                            switch (dir){
                                case LEFT -> player.setMovePosition(new Position(-i, 0));
                                case RIGHT -> player.setMovePosition(new Position(i, 0));
                                case UP -> player.setMovePosition(new Position(0, -i));
                                case DOWN -> player.setMovePosition(new Position(0, i));
                            }
                        }
                    }
                } catch (InterruptedException e){
                    return;
                }
                player.setPosition(nextPosition);
            }
        }
    }
}
