import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    public enum BoardEl{
        EMPTY,
        WALL,
        POINT,
        FOOD;

        private FoodType foodType;

        public void setFoodType(FoodType foodType){
            this.foodType = foodType;
        }

        public FoodType getFoodType() {
            return foodType;
        }
    }

    public enum FoodType{
        APPLE,      //invisible
        CHERRY,     //double points
        ORANGE,     //random teleport
        LIME,       //points disappear
        STRAWBERRY;     //invulnerable

        private static final Random random = new Random();
        public static Game.FoodType getRandomFood(){
            Game.FoodType[] foodTypes = values();
            return foodTypes[random.nextInt(foodTypes.length)];
        }

    }


    public enum Direction{
        UP,
        DOWN,
        RIGHT,
        LEFT,
        STILL;

        private static Random RANDOM = new Random();
        public static Game.Direction getRandomDirection(){
            Game.Direction[] directions = values();
            int randDir = RANDOM.nextInt(directions.length);
            return directions[randDir];
        }

        public static Game.Direction getDiffDirection(int newX, int newY){
            if(newX > 0)
                return RIGHT;
            if (newX < 0)
                return LEFT;
            if(newY > 0)
                return DOWN;
            if(newY < 0)
                return UP;

            return STILL;
        }
    }

    private final int x;
    private final int y;
    public Game.BoardEl[][] gameBoard;
    private int lives;
    private int playerScore;
    private Player player;
    private ArrayList<Ghost> ghosts;
    private UpgradeManager upgradeManager;
    private final int scoreForPoint = 100;
    private final int scoreForFood = 200;
    private double scoreFactor;
    private GameWindow gameWindow;
    private boolean pointsInvisible;

    public Game(int x, int y){

        this.x = x;
        this.y = y;

        lives = 3;
        playerScore = 0;
        initializeGameBoard();
        player = new Player(this);
        createGhosts();
        upgradeManager = new UpgradeManager(this);
        scoreFactor = 1;

        pointsInvisible = false;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Ghost> getGhosts(){
        return ghosts;
    }



    public void setGameWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }
    private void createGhosts(){
        final  int ghostsNum = Math.max(getCurrentPointsNum() / 30, 4);
        ghosts = new ArrayList<>();
        for(int i = 0; i < ghostsNum; i++){
            ghosts.add(new Ghost(this));
        }
    }

    protected void initializeGameBoard(){
        gameBoard = new Game.BoardEl[y][x];
        applyMazeOnBoard(makeMaze());
        reloadBoard();
    }


    protected void applyMazeOnBoard(int[][] maze){
        for(int row = 0; row < y; row++){
            for(int col = 0; col < x; col++){
                if(maze[row][col] == 1){
                    gameBoard[row][col] = BoardEl.WALL;
                } else gameBoard[row][col] = BoardEl.POINT;
            }
        }

      /*  maze = makeMaze();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        } */
    }

    protected int[][] makeMaze(){
        int mazeX = x;
        int mazeY = y;

        if ( mazeX % 2 == 0 )
            mazeX++;
        if ( mazeY % 2 == 0 )
            mazeY++;

        MazeGenerator mazeGenerator = new MazeGenerator();
        return mazeGenerator.getMaze(mazeY, mazeX);
    }

    protected void reloadBoard() {
        setPathToPoints();

    }

    protected void setPathToPoints() {
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(gameBoard[j][i] == BoardEl.EMPTY || gameBoard[j][i] == BoardEl.FOOD){
                    gameBoard[j][i] = BoardEl.POINT;
                }
            }
        }
    }


    protected int getCurrentPointsNum() {
        int points = 0;
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(gameBoard[j][i] == BoardEl.POINT){
                    points++;
                }
            }
        }
        return points;
    }

    protected Position getNotWallPos(){
        while(true){
            int x;
            int y;
            y = (int)(Math.random() * (this.y - 2)) + 1;
            for(int i = 0; i < this.x; i++){
                x = (int)(Math.random() * (this.x - 2)) + 1;
                if(gameBoard[y][x] != BoardEl.WALL){
                    return new Position(x, y);
                }
            }
        }
    }

    protected void handlePositionChange(){

        for(Ghost ghost : ghosts){

            if(player.getPosX() == ghost.getPosX() && player.getPosY() == ghost.getPosY()){
                ghostAtePlayer();
                break;
            }
        }
        if(upgradeManager.isActive()){
            if(player.getPosition().isEqualTo(upgradeManager.getPosition())){
                playerCollectedUpgrade();
            }
        }
    }

    private void ghostAtePlayer() {
        if(player.isInvulnerable()){
            return;
        }
        this.lives--;


        if(this.lives <= 0){
            gameEnd();
            return;
        }

        player.setDirection(Direction.STILL);
        Position pos = this.getNotWallPos();
        player.setPosition(pos);

        SwingUtilities.invokeLater(() -> gameWindow.updateLives());
    }

    protected void playerCollectedUpgrade(){
        upgradeManager.setCollected();
        setPlayerScore(scoreForFood);

    }


    protected void playerCollectedPoint(int x, int y){
        gameBoard[y][x] = BoardEl.EMPTY;
        setPlayerScore(scoreForPoint);
        if(getCurrentPointsNum() == 0){
            playerWon();
        }

    }


    public void setPlayerScore(int n) {
        this.playerScore += (int) (n * scoreFactor);
        gameWindow.updateScore();
    }

    public Position getRandomGhostPos(){
        final int i = (int)(Math.random() * ghosts.size());
        return ghosts.get(i).getPosition();
    }

    public Position getNewPositionInDirection(Position pos, Direction direction){
        Position newPosition = new Position(pos);
        int newX = 0;
        int newY = 0;
        switch (direction){
            case UP -> newY = -1;
            case DOWN -> newY = 1;
            case LEFT -> newX = -1;
            case RIGHT -> newX = 1;
        }

        if(newX == 0 && newY == 0){
            return newPosition;
        }
        newPosition.setCoordinates(pos.getX() + newX, pos.getY() + newY);
        return newPosition;
    }

    public boolean isDirectionValid(Position position, Game.Direction direction){
        Position nextPosition = getNewPositionInDirection(position, direction);
        if(nextPosition.getX() < 0 || nextPosition.getX() >= this.getX()
                || nextPosition.getY() < 0 || nextPosition.getY() >= this.getY()){
            return false;
        }
        if(gameBoard[nextPosition.getY()][nextPosition.getX()] == BoardEl.WALL){
            return false;
        }
        return true;
    }

    public void stop(){
        upgradeManager.stop();
        player.stop();
        for(Ghost ghost : ghosts){
            ghost.stop();
        }
        gameWindow.stop();
    }

    protected void gameEnd() {
        SwingUtilities.invokeLater(() -> {
            stop();
            gameWindow.setVisible(false);
            gameWindow.dispose();
            gameWindow.showGameOverDialog();
        });
    }


    private void playerWon() {
        gameWindow.dispose();
        gameWindow.showWinningDialog();
        gameEnd();
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getLives() {
        return lives;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public double getScoreFactor() {
        return scoreFactor;
    }

    public void setScoreFactor(double scoreFactor) {
        this.scoreFactor = scoreFactor;
    }

    public boolean arePointsInvisible() {
        return pointsInvisible;
    }

    public void setPointsInvisible(boolean pointsInvisible) {
        this.pointsInvisible = pointsInvisible;
    }

    public UpgradeManager getUpgradeManager() {
        return upgradeManager;
    }
}
