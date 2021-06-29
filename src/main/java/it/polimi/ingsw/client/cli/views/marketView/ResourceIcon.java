package it.polimi.ingsw.client.cli.views.marketView;

public enum ResourceIcon {

        COIN("coin"),

        SERVANT("servant"),

        SHIELD("shield"),

        STONE("stone"),

        NOTHING("nothing"),

        CROSS("cross");

        private final String[] icon_line = new String[6];
        private String[] color = new String[3];

        ResourceIcon(String icon){

            switch(icon){
                case "servant":
                    this.color[0]="\u001B[35m";
                    this.color[1]="\u001B[38;5;94m";
                    this.color[2]="\u001B[37m";

                    this.icon_line[0] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    this.icon_line[1] = color[0] + " ▓▓▓"   + this.color[2] + "▒▒▒▒▒" + color[0] + "▓▓▓ " + "\u001B[0m";
                    this.icon_line[2] = color[0] + "▓▓▓▓▓▓" + color[1] + "▒"     + color[0] + "▓▓▓▓▓▓" + "\u001B[0m";
                    this.icon_line[3] = color[0] + "▓▓▓▓▓▓" + color[1] + "▒"     + color[0] + "▓▓▓▓▓▓" + "\u001B[0m";
                    this.icon_line[4] = color[0] + " ▓▓▓▓▓" + color[1] + "▒"     + color[0] + "▓▓▓▓▓ " + "\u001B[0m";
                    this.icon_line[5] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    break;
                case "shield":
                    this.color[0]="\u001B[36m";
                    this.color[1]="\u001B[0m";
                    this.icon_line[0] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    this.icon_line[1] = color[0] + " ▓▓"   + color[1] + "▒▒▒▒▒▒▒" + color[0] + "▓▓ " + "\u001B[0m";
                    this.icon_line[2] = color[0] + "▓▓▓"   + color[1] + "▒▒▒▒▒▒▒" + color[0] + "▓▓▓" + "\u001B[0m";
                    this.icon_line[3] = color[0] + "▓▓▓▓"  + color[1] + "▒▒▒▒▒"   + color[0] + "▓▓▓▓" + "\u001B[0m";
                    this.icon_line[4] = color[0] + " ▓▓▓▓" + color[1] + "▒▒▒"     + color[0] + "▓▓▓▓ " + "\u001B[0m";
                    this.icon_line[5] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    break;

                case "stone":
                    this.color[0]="\u001B[37m";
                    this.color[1]="\u001B[37m";
                    this.icon_line[0] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    this.icon_line[1] = color[0] + " ▓▓▓" + color[1] + "▒▒▒▒▒"     + color[0] + "▓▓▓ " + "\u001B[0m";
                    this.icon_line[2] = color[0] + "▓▓"   + color[1] + "▒▒▒▒▒▒▒▒▒" + color[0] + "▓▓" + "\u001B[0m";
                    this.icon_line[3] = color[0] + "▓▓"   + color[1] + "▒▒▒▒▒▒▒▒▒" + color[0] + "▓▓" + "\u001B[0m";
                    this.icon_line[4] = color[0] + " ▓▓▓" + color[1] + "▒▒▒▒▒"     + color[0] + "▓▓▓ " + "\u001B[0m";
                    this.icon_line[5] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    break;

                case "coin":
                    this.color[0]="\u001B[93m";
                    this.color[1]="\u001B[30m";
                    this.icon_line[0] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    this.icon_line[1] = color[0] + " ▓▓▓" + color[1] + "▒▒▒▒▒" + color[0] + "▓▓▓ " + "\u001B[0m";
                    this.icon_line[2] = color[0] + "▓▓▓▓" + color[1] + "▒▒"    + color[0] + "▓▓▓▓▓▓▓" + "\u001B[0m";
                    this.icon_line[3] = color[0] + "▓▓▓▓" + color[1] + "▒▒"    + color[0] + "▓▓▓▓▓▓▓" + "\u001B[0m";
                    this.icon_line[4] = color[0] + " ▓▓▓" + color[1] + "▒▒▒▒▒" + color[0] + "▓▓▓ " + "\u001B[0m";
                    this.icon_line[5] = color[0] + "  ▓▓▓▓▓▓▓▓▓  " + "\u001B[0m";
                    break;

                case "nothing":
                    this.icon_line[0] = "             ";
                    this.icon_line[1] = "             ";
                    this.icon_line[2] = "             ";
                    this.icon_line[3] = "             ";
                    this.icon_line[4] = "             ";
                    this.icon_line[5] = "             ";

            }
        }

        public String returnLine(int line){
            return icon_line[line];
        }
}

