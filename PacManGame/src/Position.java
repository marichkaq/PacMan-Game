public class Position {
    private int posX;
    private int posY;

    public Position(){

    }

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public Position(Position other){
        this.posX = other.getX();
        this.posY = other.getY();
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setCoordinates(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public boolean isEqualTo(Position other) {
        return this.posX == other.getX() && this.posY == other.getY();
    }

}

