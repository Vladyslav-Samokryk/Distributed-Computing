import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class Main extends JFrame {
    private volatile int simpleSemaphore = 0;
    JButton buttonStart2;
    JButton buttonStop2;
    JSlider slider;
    JFrame frame;
    JPanel contentTop;
    GridLayout layoutTop;

    JPanel contentLeft;
    JPanel contentRight;
    JPanel contentBottom;
    JPanel contentMain;

    GridLayout layoutLeftRight;
    GridLayout layoutBottom;

    JButton buttonStart;
    JButton buttonStop;

    ThreadManager thread1;
    ThreadManager thread2;

    ActionListener listener;

    Logger log = Logger.getLogger(Main.class.getName());

    private void initSlider() {
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setValue(50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    }

    void initButtons() {
        buttonStart = new JButton("Start1");
        buttonStop = new JButton("Stop1");

        buttonStart2 = new JButton("Start2");
        buttonStop2 = new JButton("Stop2");

        initButtonsEnabled();
    }


    void initListener() {
        listener = e -> {
            if (e.getSource() == buttonStart) {
                if (simpleSemaphore > 0) log.info("Busy by another thread ");
                else {
                    simpleSemaphore = 1;
                    thread1 = new ThreadManager(10, slider);
                    thread1.setMinPriority();

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    buttonStart2.setEnabled(true);
                    buttonStop2.setEnabled(false);
                }

            } else if (e.getSource() == buttonStop) {

                simpleSemaphore = 0;
                thread1.setRunning(false);

                initButtonsEnabled();

            } else if (e.getSource() == buttonStart2) {
                if (simpleSemaphore > 0)  log.info("Busy by another thread ");
                else {
                    simpleSemaphore = 1;
                    thread2 = new ThreadManager(90, slider);
                    thread2.setMaxPriority();

                    buttonStart.setEnabled(true);
                    buttonStop.setEnabled(false);

                    buttonStart2.setEnabled(false);
                    buttonStop2.setEnabled(true);
                }
            } else if (e.getSource() == buttonStop2) {
                simpleSemaphore = 0;
                thread2.setRunning(false);
                initButtonsEnabled();

            }
        };

        buttonStart.addActionListener(listener);
        buttonStop.addActionListener(listener);
        buttonStart2.addActionListener(listener);
        buttonStop2.addActionListener(listener);
    }

    void initButtonsEnabled() {
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);

        buttonStart2.setEnabled(true);
        buttonStop2.setEnabled(false);
    }

    void initLayouts() {
        layoutTop = new GridLayout(1, 1);
        layoutLeftRight = new GridLayout(2, 1);
        layoutBottom = new GridLayout(1, 2);
    }


    void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentMain);
        frame.setSize(600, 300);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    void initContent() {
        contentTop = new JPanel();

        contentTop.setLayout(layoutTop);

        contentLeft = new JPanel();
        contentRight = new JPanel();
        contentBottom = new JPanel();

        contentMain = new JPanel();

        contentLeft.setLayout(layoutLeftRight);
        contentRight.setLayout(layoutLeftRight);
        contentBottom.setLayout(layoutBottom);

        contentMain.setLayout(layoutLeftRight);

        contentLeft.add(buttonStart);
        contentLeft.add(buttonStop);

        contentRight.add(buttonStart2);
        contentRight.add(buttonStop2);

        contentTop.add(slider);

        contentBottom.add(contentLeft);
        contentBottom.add(contentRight);

        contentMain.add(contentTop);
        contentMain.add(contentBottom);
    }

    public Main() {
        initSlider();
        initButtons();
        initLayouts();
        initContent();
        initListener();
        initFrame();
    }



    public static void main(String[] args) {
        Main main = new Main();
    }
}
