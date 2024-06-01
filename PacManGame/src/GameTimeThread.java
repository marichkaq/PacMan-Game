import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameTimeThread extends Thread{
    private long startTime;
    JLabel timeLabel;

    public GameTimeThread(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        while(!Thread.interrupted()){
            updateTimeLabel();
            try{
                sleep(1000);
            } catch (InterruptedException e){
                return;
            }
        }
    }

    private void updateTimeLabel() {
        SwingUtilities.invokeLater(() -> {long elapsedTime = System.currentTimeMillis() - startTime;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MINUTES.toSeconds(minutes);
        String time = String.format("Time: %02d:%02d", minutes, seconds);
        timeLabel.setText(time);
        });

    }
}

//