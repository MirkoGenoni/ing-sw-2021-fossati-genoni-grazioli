package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.StartCLI;
import it.polimi.ingsw.client.gui.GUI;

import java.util.Scanner;

public class ClientStart {
    public static void main(String[] args) {
        String input;
        boolean selection = true;

        if(args.length==0 || (!args[0].equals("CLI") && !args[0].equals("GUI"))) {
            while (selection) {
                System.out.print("\u001B[2J\u001B[3J\u001B[H");
                System.out.print("Select what you want to start (CLI or GUI): ");
                Scanner in = new Scanner(System.in);
                input = in.nextLine();
                input = input.toUpperCase();

                switch (input) {
                    case "CLI":
                        StartCLI startCLI = new StartCLI("localhost", 12345);
                        startCLI.startClient();
                        selection = false;
                        break;
                    case "GUI":
                        System.out.println("The GUI is running...");
                        GUI.main(args);
                        selection = false;
                        break;
                }
            }
        } else {
            switch (args[0].toUpperCase()) {
                case "CLI":
                    StartCLI startCLI = new StartCLI("localhost", 12345);
                    startCLI.startClient();
                    break;
                case "GUI":
                    GUI.main(args);
                    break;
            }
        }
    }
}
