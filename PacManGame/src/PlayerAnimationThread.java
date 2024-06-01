public class PlayerAnimationThread extends Thread{
    PlayerAnimation playerAnimation;

    public PlayerAnimationThread(PlayerAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            synchronized (this){
                try {
                    Thread.sleep(150);
                } catch(InterruptedException e){
                    return;
                }
                playerAnimation.swapIcon();
            }
        }
    }
}
