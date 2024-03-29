package it.polimi.ingsw.model.developmentcard;

import it.polimi.ingsw.model.exceptions.DevelopmentCardException;

import java.util.ArrayList;

/**
 * DevelopmentCardHandler manages the Player's DevelopmentCards during the play
 * @author davide grazioli
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
     * Return an Arraylist of the cards i can active the Development power
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
     * Return all the card a player has in him gameBoard
     * @return A copy of developmentCardColl that is a collection of all the cards a player has
     */
    public ArrayList<DevelopmentCard> getDevelopmentCardColl() {

        return new ArrayList<>(developmentCardColl);
    }


    /**
     * Put the development card bought in selected position, if a player choose a valid position
     * @param cardBought card the player Bought at Market and want to activate
     * @param selectedPosition The selectedPosition is the position of the card to substitute, starting from zero
     */
    public void setActiveDevelopmentCard (DevelopmentCard cardBought, int selectedPosition) throws DevelopmentCardException{

        try {
            activeDevelopmentCard[selectedPosition] = cardBought;   //2 is the max value, if I try to put in 3, throw an exception
            developmentCardColl.add(cardBought);                   //not executed if Exception is thrown
        }
        catch (ArrayIndexOutOfBoundsException e){
            //System.out.println("Can't add a card out of gameboard spaces");
            throw new DevelopmentCardException("Can't add a card out of gameboard spaces");
        }

    }


    /**
     * Check if a player could buy a card, giving him the position where he could put the bought card
     * @param cardLevel level of the card the player bought (from 1 to 3)
     * @return An arraylist with boolean that specifies for every position if the player could put the bought card in that space (true if he can, false if he can't)
     */
    public ArrayList<Boolean> checkBoughtable (int cardLevel) {

        ArrayList<Boolean> boughtablePositions = new ArrayList<>();

        if (cardLevel == 1) {
            for (int i = 0; i < 3; i++)
                if (activeDevelopmentCard[i] == null)
                    boughtablePositions.add(i, true);
                else
                    boughtablePositions.add(i,false);
        }
        else { //if cardLevel == 2 || cardLevel == 3

            for (int i = 0; i < 3; i++)
                if (activeDevelopmentCard[i] != null && activeDevelopmentCard[i].getLevel() == cardLevel - 1)
                    boughtablePositions.add(i,true);
                else
                    boughtablePositions.add(i,false);
        }
        return boughtablePositions;
    }


    /**
     * Check if a Player's collection of development card satisfy requirements of color and level in input
     * @param colorToCheck describe in an ArrayList the color of the card to check if present
     * @param levelToCheck describe in an ArrayList the color of the card to check if present
     * @return true if all the colors and them level in ArrayList are present in my collection false if not
     */

    public boolean checkDevelopmentCard (ArrayList<CardColor> colorToCheck, ArrayList<Integer> levelToCheck) throws DevelopmentCardException {

        if (colorToCheck==null || levelToCheck==null || colorToCheck.size() != levelToCheck.size())
            throw new DevelopmentCardException("Can't process check because of formatting error");

        else {   //correct format of input
            int numberOfElements = colorToCheck.size();
            ArrayList<DevelopmentCard> collection = new ArrayList<>(developmentCardColl); //duplicated list

            boolean finded = false;

            for (int i = 0; i < numberOfElements; i++) {

                for (DevelopmentCard card : collection) { //modified collection
                    if (card.getColor().name().equals(colorToCheck.get(i).name()) && card.getLevel() == levelToCheck.get(i)) { //stringhe devono essere equals se effettivamente è così
                        collection.remove(card); //can't count duplicated cards
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

    /**
     * Count the number of the Development card of a specified color a Player has
     * @param color specifies the color of a DevelopmentCard which you want to know the count
     * @return how many DevelopmentCards of a specified color a player has
     */
    public int checkCountDevelopmentCard (CardColor color){
         int count = 0;

         for (DevelopmentCard card : developmentCardColl){
             if (card.getColor().name().equals(color.name()))
                 count++;
         }

         return count;
    }

    /**
     * check if a player has at least a specified number of DevelopmentCard of a specified color
     * @param color the color to check
     * @param quantity the quantity to verify
     * @return true if the player has at least the specified number of the specified color of DevelopmentCards
     */
    public boolean checkCountDevelopmentCard (CardColor color, int quantity){
        int count = 0;

        for (DevelopmentCard card : developmentCardColl){
            if (card.getColor().name().equals(color.name()))
                count++;
        }

        return count >= quantity;
    }


}


