public class GhostMoveThread extends Thread{
    private Ghost ghost;
    private Game.Direction direction;

    public GhostMoveThread(Ghost ghost) {
        this.ghost = ghost;
        direction = Game.Direction.STILL;
    }

    @Override
    public void run() {
        Game game = ghost.getGame();
        while (!Thread.interrupted()) {
            synchronized (this) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
                if (direction == Game.Direction.STILL || Math.random() > 0.20) {
                    direction = Game.Direction.getRandomDirection();

                    if (!canMoveTo(direction)) {
                        if (canMoveTo(Game.Direction.LEFT))
                            direction = Game.Direction.LEFT;
                        else if (canMoveTo(Game.Direction.RIGHT))
                            direction = Game.Direction.RIGHT;
                        else if (canMoveTo(Game.Direction.UP))
                            direction = Game.Direction.UP;
                        else if (canMoveTo(Game.Direction.DOWN))
                            direction = Game.Direction.DOWN;
                    }
                } else {
                    Position ghostPosition = ghost.getPosition();
                    Position playerPosition = game.getPlayer().getPosition();
                    int diffX = playerPosition.getX() - ghostPosition.getX();
                    int diffY = playerPosition.getY() - ghostPosition.getY();
                    Game.Direction diffDirection = Game.Direction.getDiffDirection(diffX, 0);
                    if(canMoveTo(diffDirection) && diffX != 0){
                        direction = diffDirection;
                    } else {
                        direction = Game.Direction.getDiffDirection(0, diffY);
                    }
                }
                int moveX = 0;
                int moveY = 0;

                switch (this.direction){
                    case RIGHT -> moveX = 1;
                    case LEFT -> moveX = -1;
                    case UP -> moveY = -1;
                    case DOWN -> moveY = 1;
                }

                if(moveX == 0 && moveY == 0){
                    continue;
                }
                int nextX = ghost.getPosX() + moveX;
                int nextY = ghost.getPosY() + moveY;

                if(nextX < 0 || nextX >= game.getX() || nextY < 0 || nextY >= game.getY())
                    continue;

                if (game.gameBoard[nextY][nextX] == Game.BoardEl.WALL)
                    continue;

                ghost.setPosition(new Position(nextX, nextY));
            }
        }
    }

    private boolean canMoveTo(Game.Direction dir){
        return ghost.getGame().isDirectionValid(ghost.getPosition(), dir);
    }

}
