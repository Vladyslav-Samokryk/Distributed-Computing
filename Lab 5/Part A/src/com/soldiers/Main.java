package com.knu.ynortman.soldiers;

public class Main {
    public static void main(String[] args) {
        final int threadNum = 5;
        final Formation formation = new Formation(100, threadNum);
        for(int i = 0; i < threadNum; ++i) {
            new Thread(new Worker(formation, i*20, (i+1)*20    - 1)).start();
        }
    }
}
