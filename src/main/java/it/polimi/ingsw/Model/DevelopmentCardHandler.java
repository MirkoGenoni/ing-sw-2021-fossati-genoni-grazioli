package it.polimi.ingsw.Model;

import java.util.ArrayList;

/**
 * DevelopmentCardHandler manages the Player's DevelopmentCards during the play
 * @author davide
 */

public class DevelopmentCardHandler {

    private DevelopmentCard[] activeDevelopmentCard;
    private ArrayList<DevelopmentCard> developmentCardColl; //contains ALL the player cards

    /**
     * Constructor of the class, creates an empty collection of active and an empty collection of non-active DevelopmentCard
     */

    public DevelopmentCardHandler() {
        activeDevelopmentCard = new DevelopmentCard[3];
        developmentCardColl = new ArrayList<>();
    }

    /**
     *
     * @return A copy of activeDevelopmentCard
     */
    public ArrayList<DevelopmentCard> getActiveDevelopmentCard() {

        ArrayList<DevelopmentCard> copyToReturn = new ArrayList<>();

        for (int i=0; i<3; i++) {
            if (activeDevelopmentCard[i]==null)
                copyToReturn.add(i,null);
            else
                copyToReturn.add(i,activeDevelopmentCard[i]);
        }
        return copyToReturn;

    }

    /**
     *
     * @return A copy of developmentCardColl that is a collection of all the cards a player has
     */
    public ArrayList<DevelopmentCard> getDevelopmentCardColl() {

        return new ArrayList<>(developmentCardColl);
    }


    /**
     * TODO adding throw of DevCard exception if selectedPosition > 2
     * @param cardBought card the player Bought at Market and want to activate
     * @param selectedPosition The selectedPosition is the position of the card to substitute, starting from zero
     */
    public void setActiveDevelopmentCard(DevelopmentCard cardBought, int selectedPosition) {

        try {
            activeDevelopmentCard[selectedPosition] = cardBought;   //2 is the max value, if I try to put in 3, throw an exception
            developmentCardColl.add(cardBought);                   //not executed if Exception is thrown
        }
        catch (ArrayIndexOutOfBoundsException e){
            //System.out.println("Can't add a card out of gameboard spaces");
            //throw new...
        }

    }


    /**
     *
     * @param cardLevel level of the card the player bought (from 1 to 3)
     * @return An arraylist with the positions where the player could put the bought card
     */
    public ArrayList<Integer> checkBoughtable (int cardLevel) {

        ArrayList<Integer> boughtablePositions = new ArrayList<>();

        if (cardLevel == 1) {
            for (int i = 0; i < 3; i++)
                if (activeDevelopmentCard[i] == null)
                    boughtablePositions.add(i);
        }
        else { //if cardLevel == 2 || cardLevel == 3

            for (int i = 0; i < 3; i++)
                if (activeDevelopmentCard[i] != null && activeDevelopmentCard[i].getLevel() == cardLevel - 1)
                    boughtablePositions.add(i);

        }
        return boughtablePositions;
    }


    /**
     *
     * TODO it is UNACCEPTABLE that color.size() != level.size() adding error
     * @param colorToCheck describe in an ArrayList the color of the card to check if present
     * @param levelToCheck describe in an ArrayList the color of the card to check if present
     * @return true if all the colors and them level in ArrayList are present in my collection false if not
     */

    public boolean checkDevelopmentCard (ArrayList<String> colorToCheck, ArrayList<Integer> levelToCheck){
        int numberOfElements = colorToCheck.size();
        boolean finded = false;

        for (int i = 0; i < numberOfElements; i++){

            for (DevelopmentCard card : developmentCardColl){
                if (card.getColor().equals(colorToCheck.get(i)) && card.getLevel() == levelToCheck.get(i)) { //stringhe devono essere equals se effettivamente è così
                    finded = true;
                    break;
                }
            }

            if (finded)                             //faccio ciclare la carta su tutta la collection. Se la trova mette a true un parametro finded
                finded = false;                     //se non la trova finded rimane falso. Se l'ho trovata (finded==true) allora lo metto a falso e vado avanti
            else                                    //altrimenti ritorno falso direttamente. Alla fine delle carte richieste se non ho mai tornato falso esco dal ciclo
                return false;                       //e torno vero
        }

        return true;

    }


}


