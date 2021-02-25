package Part_C;

import java.util.LinkedList;

public class MainClass {
    private final int numberOfMonks;
    private LinkedList<Monk> monks;
    private MonkFactory monkFactory;
    private Winner winner;

    MainClass(int numberOfMonks) {
        this.numberOfMonks = numberOfMonks;
        monks = new LinkedList<>();
        monkFactory = new MonkFactory();
        winner = new Winner();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass(8);
        mainClass.start();
    }

    void start() {
        for (int i = 0; i < numberOfMonks; i++)
            monks.add(monkFactory.getNext());

        System.out.println(monks);

        new Fight(winner, monks);

        while (!winner.isWinnerSet()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(winner);
    }
}
