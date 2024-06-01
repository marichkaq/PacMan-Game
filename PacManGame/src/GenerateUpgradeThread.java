public class GenerateUpgradeThread extends Thread{
    private UpgradeManager upgradeManager;

    public GenerateUpgradeThread(UpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            synchronized (this){
                try{
                    Thread.sleep(5000);
                } catch (InterruptedException e){
                    return;
                }
                if(upgradeManager.isActive())
                    continue;
                if(Math.random() > 0.25)
                    continue;
                upgradeManager.setActive();
            }
        }
    }
}
