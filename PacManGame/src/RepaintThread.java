public class RepaintThread extends Thread{
    GameWindow gameWindow;

    public RepaintThread(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }


    @Override
    public void run() {
        synchronized (this){
            while(!Thread.interrupted()){
                try{
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    return;
                }
                gameWindow.getBoard().repaint();
            }
        }
    }
}
















