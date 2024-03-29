package it.polimi.ingsw.client.cli.views.productionview;

import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents the visualization of a development card
 *
 * @author Mirko Genoni
 */
public class DevelopmentCardVisualization {

    private final int cardNumber;
    private final int victoryPoints;
    private final String cardColor;
    private final DevelopmentCardSymbols level;
    private final ArrayList<DevelopmentCardSymbols> cost;
    private final ArrayList<Integer> costNumbers;
    private final ArrayList<DevelopmentCardSymbols> requirements;
    private final ArrayList<Integer> requirementsNumber;
    private final ArrayList<DevelopmentCardSymbols> production;
    private final ArrayList<Integer> productionNumber;

    private String[] developmentCard;

    /**
     * The constructor of the class initializes all the data structure of a normal development card
     *
     * @param cardNumber indicates the number of card created
     * @param color indicates the color of the card
     * @param victoryPoints indicates the number of victory points given by the card
     * @param level indicates the level of the card
     * @param cost indicates the cost of buy of the card
     * @param materialRequired indicates the material needed for the activation
     * @param productionResult indicates the resources given by the card
     */
    public DevelopmentCardVisualization(int cardNumber, String color, int victoryPoints, String level, Map<Resource, Integer> cost, Map<Resource, Integer> materialRequired, Map<ProductedMaterials, Integer> productionResult){

        this.cardNumber = cardNumber;
        this.cardColor = colorConvertion(color);
        this.level = DevelopmentCardSymbols.valueOf(level);
        this.victoryPoints = victoryPoints;

        int[] size = new int[3];

        size[0] = cost.size();
        size[1] = materialRequired.size();
        size[2] = productionResult.size();

        this.cost = new ArrayList<>();
        this.requirements = new ArrayList<>();
        this.production = new ArrayList<>();
        this.costNumbers = new ArrayList<>();
        this.requirementsNumber = new ArrayList<>();
        this.productionNumber = new ArrayList<>();

        int i=0;

        for(Resource r : cost.keySet()){
            this.cost.add(DevelopmentCardSymbols.valueOf(r.toString()));
            costNumbers.add(cost.get(r));
            i++;
        }

        int j=0;

        for(Resource r : materialRequired.keySet()){
            this.requirements.add(DevelopmentCardSymbols.valueOf(r.toString()));
            this.requirementsNumber.add(materialRequired.get(r));
            j++;
        }

        int k=0;

        for(ProductedMaterials r : productionResult.keySet()){
            this.production.add(DevelopmentCardSymbols.valueOf(r.toString()));
            this.productionNumber.add(productionResult.get(r));
            k++;
        }

        saveCard(size);
    }

    /**
     * This constructor initializes a blank card, placeholder for a empty space
     *
     * @param i is a random initializer
     */
    public DevelopmentCardVisualization(int i) {
        this.cardNumber = 0;
        this.victoryPoints = 0;
        this.cardColor = "";
        this.level = null;
        this.cost = null;
        this.costNumbers = null;
        this.requirements = null;
        this.requirementsNumber = null;
        this.production = null;
        this.productionNumber = null;
        this.developmentCard = new String[37];

        for (int j = 0; j < 27; j++) {
            this.developmentCard[j] = "                               ";
        }
    }

    /**
     * This card is a placeholder that indicates that a development card cannot be positioned in a space
     * @param s is a random initializer
     */
    public DevelopmentCardVisualization(String s){
        this.cardNumber = 0;
        this.victoryPoints = 0;
        this.cardColor = "";
        this.level = null;
        this.cost = null;
        this.costNumbers = null;
        this.requirements = null;
        this.requirementsNumber = null;
        this.production = null;
        this.productionNumber = null;
        this.developmentCard = new String[37];

        this.developmentCard[0] = "                               ";
        this.developmentCard[1] = "                               ";
        this.developmentCard[2] = "                               ";
        this.developmentCard[3] = "                               ";
        this.developmentCard[4] = "                               ";
        this.developmentCard[5] = "      ██               ██      ";
        this.developmentCard[6] = "       ██             ██       ";
        this.developmentCard[7] = "        ██           ██        ";
        this.developmentCard[8] = "         ██         ██         ";
        this.developmentCard[9] = "          ██       ██          ";
        this.developmentCard[10] = "           ██     ██           ";
        this.developmentCard[11] = "            ██   ██            ";
        this.developmentCard[12] = "             ██ ██             ";
        this.developmentCard[13] = "              ███              ";
        this.developmentCard[14] = "             ██ ██             ";
        this.developmentCard[15] = "            ██   ██            ";
        this.developmentCard[16] = "           ██     ██           ";
        this.developmentCard[17] = "          ██       ██          ";
        this.developmentCard[18] = "         ██         ██         ";
        this.developmentCard[19] = "        ██           ██        ";
        this.developmentCard[20] = "       ██             ██       ";
        this.developmentCard[21] = "      ██               ██      ";
        this.developmentCard[22] = "                               ";
        this.developmentCard[23] = "                               ";
        this.developmentCard[24] = "                               ";
        this.developmentCard[25] = "                               ";
        this.developmentCard[26] = "                               ";
    }

    /**
     * Convert a String that indicates a color in the correspondent escape code
     * @param color is the color asked
     * @return the escape code
     */
    private String colorConvertion(String color){
        switch(color) {
            case "YELLOW":
                return ("\u001B[93m");

            case "GREEN":
                return ("\u001B[32m");

            case "PURPLE":
                return ("\u001B[35m");

            case "BLUE":
                return ("\u001B[36m");

            default:
                return "";
        }
    }


    /**
     * Saves the visualization of the card
     *
     * @param size
     */
    private void saveCard(int[] size){
        this.developmentCard = new String[27];

        this.developmentCard[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
        this.developmentCard[1] = "┃     DEVELOPMENT CARD #"+ this.cardNumber + "     ┃";
        this.developmentCard[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.developmentCard[3] = "┃            LEVEL            ┃";
        this.developmentCard[4] = "┃           " + cardColor + level.returnLine(0) + "\u001B[0;00m" + "           ┃";
        this.developmentCard[5] = "┃           " + cardColor + level.returnLine(1) + "\u001B[0;00m" + "           ┃";
        this.developmentCard[6] = "┃           " + cardColor + level.returnLine(2) + "\u001B[0;00m" + "           ┃";
        this.developmentCard[7] = "┃           " + cardColor + level.returnLine(3) + "\u001B[0;00m" + "           ┃";
        this.developmentCard[8] = "┃           " + cardColor + level.returnLine(4) + "\u001B[0;00m" + "           ┃";
        this.developmentCard[9] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.developmentCard[10] = "┃            COSTS            ┃";

        printResource(this.cost, 11, this.costNumbers);

        this.developmentCard[14] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.developmentCard[15] = "┃          REQUIREMENT        ┃";

        printResource(this.requirements, 16, this.requirementsNumber);

        this.developmentCard[19] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.developmentCard[20] = "┃           PRODUCE           ┃";

        printResource(this.production, 21, this.productionNumber);

        this.developmentCard[24] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        if(this.victoryPoints<10)
            this.developmentCard[25] = "┃      WINNER POINTS:  " + this.victoryPoints + "      ┃";
        else
            this.developmentCard[25] = "┃      WINNER POINTS:  " + this.victoryPoints + "     ┃";
        this.developmentCard[26] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
    }

    /**
     * Saves part of the card, in particular the costs of activation and the result
     * @param in represent the symbol colored by the resource type
     * @param position indicates if is a cost of activation or a result
     * @param numbers indicates the multiplicity of the resource
     */
    private void printResource(ArrayList<DevelopmentCardSymbols> in, int position, ArrayList<Integer> numbers){
        switch(in.size()){
            case 1:
                this.developmentCard[position] = "┃             " + in.get(0).returnLine(0) + "           ┃";
                this.developmentCard[position + 1] = "┃           " + numbers.get(0) + "x" + in.get(0).returnLine(1) + "           ┃";
                this.developmentCard[position + 2] = "┃             " + in.get(0).returnLine(2) + "           ┃";
                break;

            case 2:
                this.developmentCard[position] = "┃        " + in.get(0).returnLine(0) + "     " + in.get(1).returnLine(0) + "      ┃";
                this.developmentCard[position + 1] = "┃      " + numbers.get(0) + "x" + in.get(0).returnLine(1) + "   " + numbers.get(1) + "x" + in.get(1).returnLine(1) + "      ┃";
                this.developmentCard[position + 2] = "┃        " + in.get(0).returnLine(2) + "     " + in.get(1).returnLine(2) + "      ┃";
                break;

            case 3:
                this.developmentCard[position] = "┃    " + in.get(0).returnLine(0) + "    " + in.get(1).returnLine(0) + "    " + in.get(2).returnLine(0) + "  ┃";
                this.developmentCard[position + 1] = "┃  " + numbers.get(0) + "x" + in.get(0).returnLine(1) + "  " + numbers.get(1) + "x" + in.get(1).returnLine(1) + "  " + numbers.get(2) + "x" + in.get(2).returnLine(1) + "  ┃";
                this.developmentCard[position + 2] = "┃    " + in.get(0).returnLine(2) + "    " + in.get(1).returnLine(2) + "    " + in.get(2).returnLine(2) + "  ┃";
        }
    }

    /**
     * returns a line of the representation
     *
     * @param line contains the line asked
     * @return the line asked
     */
    public String printCard(int line){
        return developmentCard[line];
    }

    /**
     * Returns the number of the card
     * @return is the number of the card
     */
    public int getCardNumber() {
        return cardNumber;
    }
}
