package it.polimi.ingsw.client.cli.views.productionview;

/**
 * This class contains the representation of an additional production
 *
 * @author Mirko Genoni
 */
public class AdditionalProductionVisualization {
    String[] additionalProduction;

    /**
     * Constructor of the class initializes all the data structure
     * @param type represents the material type asked by the additional production
     */
    AdditionalProductionVisualization(String type){
        DevelopmentCardSymbols symbolLVL = DevelopmentCardSymbols.valueOf("LEADER");
        DevelopmentCardSymbols production1 = DevelopmentCardSymbols.valueOf("ANYTHING");
        DevelopmentCardSymbols production2 = DevelopmentCardSymbols.valueOf("FAITHPOINT");

        String colorProduction;
        DevelopmentCardSymbols typeRequired = DevelopmentCardSymbols.valueOf(type);

        switch (type){
            case "COIN":
                colorProduction = "\u001B[93m";
                break;

            case "SHIELD":
                colorProduction = "\u001B[36m";
                break;

            case "STONE":
                colorProduction = "\u001B[1;37m";
                break;

            case "SERVANT":
                colorProduction = "\u001B[35m";
                break;

            default:
                colorProduction = "";
        }

        saveProduction(colorProduction, symbolLVL, typeRequired, production1, production2);
    }

    /**
     * Saves the visualization of the additional production
     *
     * @param colorProduction is the color of the production
     * @param symbol is the symbol of the production
     * @param typeRequired are the material required
     * @param production1 is the first result
     * @param production2 is the seconda result
     */
    private void saveProduction(String colorProduction, DevelopmentCardSymbols symbol, DevelopmentCardSymbols typeRequired, DevelopmentCardSymbols production1, DevelopmentCardSymbols production2){
        this.additionalProduction = new String[25];
        this.additionalProduction[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
        this.additionalProduction[1] = "┃    ADDITIONAL PRODUCTION    ┃";
        this.additionalProduction[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.additionalProduction[3] = "┃                             ┃";
        this.additionalProduction[4] = "┃           " + symbol.returnLine(0)+ "           ┃";
        this.additionalProduction[5] = "┃           " + symbol.returnLine(1)+ "           ┃";
        this.additionalProduction[6] = "┃           " + symbol.returnLine(2)+ "           ┃";
        this.additionalProduction[7] = "┃           " + symbol.returnLine(3)+ "           ┃";
        this.additionalProduction[8] = "┃           " + symbol.returnLine(4)+ "           ┃";
        this.additionalProduction[9] = "┃                             ┃";
        this.additionalProduction[10] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.additionalProduction[11] = "┃          REQUIREMENT        ┃";
        this.additionalProduction[12] = "┃                             ┃";
        this.additionalProduction[13] = "┃             " + typeRequired.returnLine(0) + "           ┃";
        this.additionalProduction[14] = "┃           1x" + typeRequired.returnLine(1) + "           ┃";
        this.additionalProduction[15] = "┃             " + typeRequired.returnLine(2) + "           ┃";
        this.additionalProduction[16] = "┃                             ┃";
        this.additionalProduction[17] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
        this.additionalProduction[18] = "┃           PRODUCE           ┃";
        this.additionalProduction[19] = "┃                             ┃";
        this.additionalProduction[20] = "┃        " + production1.returnLine(0) + "     " + production2.returnLine(0) + "      ┃";
        this.additionalProduction[21] = "┃      1x" + production1.returnLine(1) + "   1x" + production2.returnLine(1) + "      ┃";
        this.additionalProduction[22] = "┃        " + production1.returnLine(2) + "     " + production2.returnLine(2) + "      ┃";
        this.additionalProduction[23] = "┃                             ┃";
        this.additionalProduction[24] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
    }

    /**
     * returns a line of the representation
     * @param in is the line asked
     * @return the line asked
     */
    public String returnLine(int in){
        return additionalProduction[in];
    }
}
