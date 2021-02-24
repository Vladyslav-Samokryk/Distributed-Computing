import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadManager implements Runnable {
    private final int target;
    private final JSlider slider;
    private boolean isRunning = true;
    private final Thread thread;

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        while (isRunning) {
            setSlider(target);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMinPriority() {
        thread.setPriority(Thread.MIN_PRIORITY);
    }

    public void setMaxPriority() {
        thread.setPriority(Thread.MAX_PRIORITY);
    }

    public ThreadManager(int target, JSlider slider) {
        this.target = target;
        this.slider = slider;
        this.thread = new Thread(this);
        this.thread.start();
    }

    private synchronized void setSlider(int target) {
        slider.setValue(target);
    }
}
