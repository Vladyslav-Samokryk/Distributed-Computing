import java.util.concurrent.atomic.AtomicBoolean;

public class Nechiporchuk extends Thread {
    private int sum = 0;
    private AtomicBoolean isWorking;
    private AtomicBoolean loaderWorking;
    private ItemQueue queue;

    Nechiporchuk(ItemQueue queue, AtomicBoolean loaderWorking, AtomicBoolean isWorking) {
        this.queue = queue;
        this.loaderWorking = loaderWorking;
        this.isWorking = isWorking;
    }

    @Override
    public void run() {
        Item item;
        while (isWorking.get() || !queue.isEmpty()) {
            if (!loaderWorking.get()) {
                isWorking.set(false);
            }
            item = queue.get();
            try {
                sleep(Settings.DELAY);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            sum += item.getPrice();
            System.out.println("Nechiporchuk got item " + ANSI.BRIGHT_BLACK + "#" + item.getCode() +
                    ANSI.RESET + " : " + ANSI.BRIGHT_BLACK + "$" + item.getPrice() + ANSI.RESET
            + ". Total sum is " + ANSI.BRIGHT_BLACK + "$" + sum + ANSI.RESET);
        }
        System.out.println(ANSI.BRIGHT_BLACK + "We stolen items for total of " +
                ANSI.BRIGHT_BLACK + "$" + sum + ANSI.BRIGHT_BLACK + "!" + ANSI.RESET);
    }
}
