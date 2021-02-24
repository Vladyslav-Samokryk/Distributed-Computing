import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ivanov extends Thread {
    private AtomicBoolean isWorking;
    AtomicBoolean loaderWorking;
    private ItemQueue queue;
    private Random random = new Random(System.currentTimeMillis());
    private int stolenItems = 0;

    Ivanov(ItemQueue queue, AtomicBoolean isWorking, AtomicBoolean loaderWorking) {
        this.queue = queue;
        this.isWorking = isWorking;
        this.loaderWorking = loaderWorking;
    }

    @Override
    public void run() {
        while (isWorking.get()) {
            Item newItem = new Item(stolenItems,
                    random.nextInt(50) * 100 + 99);
            queue.add(newItem);
            stolenItems++;
            System.out.println("Ivanov put item " + ANSI.BRIGHT_BLACK + "#" + newItem.getCode() +
                    ANSI.RESET + " : " + ANSI.BRIGHT_BLACK + "$" + newItem.getPrice() + ANSI.RESET);
            if (stolenItems >= Settings.ALL_ELEMENTS) {
                System.out.println(ANSI.BRIGHT_BLACK + "Items have ended!" + ANSI.RESET);
                isWorking.set(false);
                loaderWorking.set(false);
            }
            try {
                sleep(Settings.DELAY);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
