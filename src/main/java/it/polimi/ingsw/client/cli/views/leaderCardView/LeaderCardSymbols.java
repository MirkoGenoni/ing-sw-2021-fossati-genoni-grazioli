package it.polimi.ingsw.client.cli.views.leaderCardView;

public enum LeaderCardSymbols {
    SERVANT("servant"),
    SHIELD("shield"),
    STONE("stone"),
    COIN("coin"),
    PRODUTCIONLVL1("productionlvl1"),
    PRODUTCIONLVL2("productionlvl2");

    private final String[] rapresentation;

    LeaderCardSymbols(String type) {

        rapresentation = new String[5];

        switch(type){
            case "servant":
                this.rapresentation[0] = "\u001B[35m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[35m" + "▒▒" + "\u001B[37m" +"▓▓▓▓▓" + "\u001B[35m" + "▒▒" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[35m" + "▒▒▒▒" + "\u001B[38;5;94m" + "▓" + "\u001B[35m" +"▒▒▒▒" + "\u001B[0;00m";
                this.rapresentation[3] = "\u001B[35m" + "▒▒▒▒" + "\u001B[38;5;94m" + "▓" + "\u001B[35m" + "▒▒▒▒" + "\u001B[0;00m";
                this.rapresentation[4] = "\u001B[35m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                break;

            case "shield":
                this.rapresentation[0] = "\u001B[36m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[36m" + "▒▒" + "\u001B[0;00m" +"▓▓▓▓▓" + "\u001B[36m" + "▒▒" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[36m" + "▒▒" + "\u001B[0;00m" +"▓▓▓▓▓" + "\u001B[36m" + "▒▒" + "\u001B[0;00m";
                this.rapresentation[3] = "\u001B[36m" + "▒▒▒" + "\u001B[0;00m" +"▓▓▓" + "\u001B[36m" + "▒▒▒" + "\u001B[0;00m";
                this.rapresentation[4] = "\u001B[36m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                break;

            case "stone":
                this.rapresentation[0] = "\u001B[37m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[37m" + "▒▒▓▓▓▓▓▒▒" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[37m" + "▒▒▓▓▓▓▓▒▒" + "\u001B[0;00m";
                this.rapresentation[3] = "\u001B[37m" + "▒▒▓▓▓▓▓▒▒" + "\u001B[0;00m";
                this.rapresentation[4] = "\u001B[37m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                break;

            case "coin":
                this.rapresentation[0] = "\u001B[93m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[93m" + "▒▒▒" + "\u001B[30m" + "▓▓▓" + "\u001B[93m" + "▒▒▒" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[93m" + "▒▒▒" + "\u001B[30m" + "▓" + "\u001B[93m" + "▒▒▒▒▒" + "\u001B[0;00m";
                this.rapresentation[3] = "\u001B[93m" + "▒▒▒" + "\u001B[30m" + "▓▓▓" + "\u001B[93m" + "▒▒▒" + "\u001B[0;00m";
                this.rapresentation[4] = "\u001B[93m" + " ▒▒▒▒▒▒▒ " + "\u001B[0;00m";
                break;

            case "productionlvl1":
                this.rapresentation[0] = " ▛▀▀▀▀▀▜ ";
                this.rapresentation[1] = " ▌     ▐ ";
                this.rapresentation[2] = " ▌  •  ▐ ";
                this.rapresentation[3] = " ▌     ▐ ";
                this.rapresentation[4] = " ▙▞▚▄▞▚▟ ";
                break;

            case "productionlvl2":
                this.rapresentation[0] = " ▛▀▀▀▀▀▜ ";
                this.rapresentation[1] = " ▌  •  ▐ ";
                this.rapresentation[2] = " ▌  •  ▐ ";
                this.rapresentation[3] = " ▌     ▐ ";
                this.rapresentation[4] = " ▙▞▚▄▞▚▟ ";
                break;

            default:
                this.rapresentation[0] = "         ";
                this.rapresentation[1] = "         ";
                this.rapresentation[2] = "         ";
                this.rapresentation[3] = "         ";
                this.rapresentation[4] = "         ";
        }
    }

    public String printLine(int line) {
        return rapresentation[line];
    }
}
