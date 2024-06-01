public class Player {
    private Game game;
    private Position position;
    private Position movePosition;
    private Game.Direction direction;
    private Game.Direction newDirection;
    private int speed;
    private boolean invisible = false;
    private boolean invulnerable = false;
    protected final int moveSteps = 8;
    private PlayerMoveThread moveThread;
    private PlayerAnimation playerAnimation;
    public Player(Game game) {
        this.game = game;
        speed = 180;
        position = game.getNotWallPos();
        direction = Game.Direction.STILL;
        newDirection = Game.Direction.STILL;
        movePosition = new Position();
        playerAnimation = new PlayerAnimation(this);
        moveThread = new PlayerMoveThread(this);
        moveThread.start();
    }

    public boolean isInvisible() {
        return invisible;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public int getPosX() {
       return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public Position getPosition() {
        return new Position(position);
    }

    public void stop() {
        //interrupt move thread
        //interrupt animation thread
        moveThread.interrupt();
        playerAnimation.stop();
    }

    public Game.Direction getDirection() {
        return direction;
    }

    public void setDirection(Game.Direction dir) {
        this.direction = dir;
    }

    public Game.Direction getNewDirection() {
        return newDirection;
    }

    public void setNewDirection(Game.Direction newDirection) {
        this.newDirection = newDirection;
    }

    public Position getMovePosition() {
        return movePosition;
    }

    public void setMovePosition(Position movePosition) {
        this.movePosition = movePosition;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPosition(Position position) {
        this.position = new Position(position);
        this.setMovePosition(new Position(0, 0));
        int x = position.getX();
        int y = position.getY();
        if (game.gameBoard[y][x] == Game.BoardEl.POINT && !game.arePointsInvisible()) {
            game.playerCollectedPoint(x, y);
        }
        game.handlePositionChange();
    }

    public Game getGame() {
        return game;
    }

    public int getSpeed() {
        return speed;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
}
