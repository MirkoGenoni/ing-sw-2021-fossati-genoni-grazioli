package it.polimi.ingsw.client.cli.views;

/**
 * This class handles the visualization of all the notification sent by the server, it prints the message inside
 * a box centered inside the page
 *
 * @author Mirko Genoni
 */
public class Messages {
    String message;
    boolean error;

    /**
     * Constructor of the class initializes all the data structures
     *
     * @param message is the content of the notification sent by the server
     * @param error represent if the message is an error or a simple notification
     */
    public Messages(String message, boolean error){
        this.error = error;
        this.message = message;
    }

    /**
     * This method handles the print of the message inside the box and centres it
     */
    public void printMessage(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        if(error)
            System.out.print("\u001B[31m");

        for(int i=0; i<24; i++)
            System.out.println("                                                                                                                           ");

        for(int j=0; j< (123 - (this.message.length() +4))/2; j++){
            System.out.print(" ");
        }

        System.out.print("┌─");

        for(int k=0; k<message.length(); k++)
            System.out.print("─");

        System.out.print("─┐\n");

        for(int j=0; j<(123 - (this.message.length() +4))/2; j++){
            System.out.print(" ");
        }

        System.out.print("│ " + message.toUpperCase() + " │\n");

        for(int j=0; j< ((123 - (this.message.length()+4))/2); j++){
            System.out.print(" ");
        }

        System.out.print("└─");

        for(int k=0; k<message.length(); k++)
            System.out.print("─");

        System.out.print("─┘");

        for(int i=0; i<24; i++)
            System.out.println("                                                                                                                           ");

        System.out.print("\u001B[0;00m");
    }
}
