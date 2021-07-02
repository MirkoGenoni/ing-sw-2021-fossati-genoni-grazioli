package it.polimi.ingsw.client.cli;

import java.util.concurrent.TimeUnit;

/**
 * This class is only a thread that prints 3 dots in sequence and then delete them, it's implemented as a
 * class extending thread because the thread will be interrupted and it's necessary to create always a new thread
 * because an interrupted thread cannot be restarted
 *
 * @author Mirko Genoni
 */
public class WaitingOtherPlayers extends Thread {
    /**
     * This method prints 3 dots in sequence and then delete them
     */
    @Override
    public void run() {
        System.out.print("                                                                                                                          \n" +
                "                                                                                                                          \n" +
                "                                                                                                                          \n");
        System.out.print("                                    WAITING FOR OTHER PLAYERS");

        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.print("\u001B[2J\u001B[3J\u001B[H");
                break;
            }

            for (int i = 0; i < 5; i++)
                System.out.print("\b");

            System.out.print("     ");

            for (int i = 0; i < 5; i++)
                System.out.print("\b");

        }    }
}
