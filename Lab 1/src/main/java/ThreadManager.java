import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadManager implements Runnable {
    private final int target;
    private final JSlider slider;
    private boolean isRunning = true;
    private final Thread thread;
    Logger log = Logger.getLogger(ThreadManager.class.getName());

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


    public ThreadManager(int target, JSlider slider) {
        this.target = target;
        this.slider = slider;
        this.thread = new Thread(this);
        this.thread.start();
    }

    private synchronized void setSlider(int target) {
        slider.setValue(target);
    }

    public void increasePriority() {
        int priority = thread.getPriority();
        if (priority + 1 <= Thread.MAX_PRIORITY) {
            thread.setPriority(priority + 1);

            log.log(Level.INFO, "thread # {0} increase priority to {1}", new Object[]{thread, priority + 1});
        }
    }

    public void decreasePriority() {
        int priority = thread.getPriority();
        if (priority - 1 >= Thread.MIN_PRIORITY) {
            thread.setPriority(priority - 1);
            log.log(Level.INFO, "thread # {0} decrease priority to {1}", new Object[]{thread.getName(), priority - 1});
        }
    }
}
