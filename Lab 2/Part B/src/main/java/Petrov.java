import java.util.concurrent.atomic.AtomicBoolean;

public class Petrov extends Thread {
    private AtomicBoolean isWorking;
    private AtomicBoolean thiefWorking;
    private AtomicBoolean accountantWorking;
    private ItemQueue fromThief;
    private ItemQueue toAccountant;

    Petrov(ItemQueue fromThief, ItemQueue toAccountant,
           AtomicBoolean thiefWorking, AtomicBoolean isWorking, AtomicBoolean accountantWorking) {
        this.fromThief = fromThief;
        this.toAccountant = toAccountant;
        this.isWorking = isWorking;
        this.thiefWorking = thiefWorking;
        this.accountantWorking = accountantWorking;
    }

    @Override
    public void run() {
        Item item;
        while (isWorking.get() || !fromThief.isEmpty()) {
            item = fromThief.get();
            System.out.println("Petrov loaded item " + ANSI.BRIGHT_BLACK + "#" + item.getCode() +
                    ANSI.RESET + " : " + ANSI.BRIGHT_BLACK + "$" + item.getPrice() + ANSI.RESET);
            try {
                sleep(Settings.DELAY);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            toAccountant.add(item);
            if (fromThief.isEmpty() && !thiefWorking.get()) {
                accountantWorking.set(false);
            }
        }
    }
}
