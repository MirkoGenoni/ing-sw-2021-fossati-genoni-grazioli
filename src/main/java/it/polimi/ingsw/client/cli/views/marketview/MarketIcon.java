package it.polimi.ingsw.client.cli.views.marketview;

/**
 * This class contains all the symbols needed to represent the marble of the market
 *
 * @author Mirko Genoni
 */
public enum MarketIcon {

        COIN("coin", "\u001B[93m"),

        SERVANT("servant", "\u001B[35m"),

        SHIELD("shield", "\u001B[36m"),

        STONE("stone","\u001B[37m"),

        NOTHING("nothing", "\u001B[0;00m"),

        FAITH("faith", "\u001B[31m");

        private final String[] icon_line = new String[7];

    /**
     * Saves the visualization of the marble
     * @param icon is the icon type needed (coin, shield, servant, stone, faithpoint, nothing)
     * @param color contains the marble color
     */
    MarketIcon(String icon, String color){
            String backGroundColor = "\u001B[0;00m";

            this.icon_line[0] = color + "  ▓▓▓▓▓▓▓▓▓  " + backGroundColor;
            this.icon_line[1] = color + " ▓▓▓▓▓▓▓▓▓▓▓ " + backGroundColor;
            this.icon_line[2] = color + "▓▓▓▓▓▓▓▓▓▓▓▓▓" + backGroundColor;
            this.icon_line[3] = color + "▓▓▓▓▓▓▓▓▓▓▓▓▓" + backGroundColor;
            this.icon_line[4] = color + "▓▓▓▓▓▓▓▓▓▓▓▓▓" + backGroundColor;
            this.icon_line[5] = color + " ▓▓▓▓▓▓▓▓▓▓▓ " + backGroundColor;
            this.icon_line[6] = color + "  ▓▓▓▓▓▓▓▓▓  " + backGroundColor;

        }

    /**
     * Returns a line of the representation
     * @param line is the number of line asked
     * @return the line asked
     */
    public String returnLine(int line){
            return icon_line[line];
        }
}
