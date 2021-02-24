import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        AtomicBoolean thiefWorking = new AtomicBoolean(true);
        AtomicBoolean loaderWorking = new AtomicBoolean(true);
        AtomicBoolean accountantWorking = new AtomicBoolean(true);

        ItemQueue fromThief = new ItemQueue(Settings.MAX_ELEMENTS);
        ItemQueue toAccountant = new ItemQueue(Settings.MAX_ELEMENTS);

        Ivanov thief = new Ivanov(fromThief, thiefWorking, loaderWorking);
        Petrov loader = new Petrov(fromThief, toAccountant, thiefWorking, loaderWorking, accountantWorking);
        Nechiporchuk account = new Nechiporchuk(toAccountant, loaderWorking, accountantWorking);

        thief.start();
        loader.start();
        account.start();

        try {
            account.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
