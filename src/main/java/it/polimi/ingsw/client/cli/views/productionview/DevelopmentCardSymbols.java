package it.polimi.ingsw.client.cli.views.productionview;

public enum DevelopmentCardSymbols {
    SERVANT("servant"),
    SHIELD("shield"),
    STONE("stone"),
    COIN("coin"),
    FAITHPOINT("faithpoint"),
    LVL1("1"),
    LVL2("2"),
    LVL3("3"),
    LEADER("leader"),
    ANYTHING("anything"),
    CROSS("cross");

    private final String[] rapresentation;
    private int number;

    DevelopmentCardSymbols(String type) {
        rapresentation = new String[6];

        switch (type) {
            case "servant":
                this.rapresentation[0] = "\u001B[35m" + "┌───┐" + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[35m" + "│ █ │" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[35m" + "└───┘" + "\u001B[0;00m";
                break;

            case "shield":
                this.rapresentation[0] = "\u001B[36m" + "┌───┐" + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[36m" + "│ █ │" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[36m" + "└───┘" + "\u001B[0;00m";
                break;

            case "stone":
                this.rapresentation[0] = "\u001B[37m" + "┌───┐" + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[37m" + "│ █ │" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[37m" + "└───┘" + "\u001B[0;00m";
                break;

            case "coin":
                this.rapresentation[0] = "\u001B[93m" + "┌───┐" + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[93m" + "│ █ │" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[93m" + "└───┘" + "\u001B[0;00m";
                break;

            case "faithpoint":
                this.rapresentation[0] = "┌───┐";
                this.rapresentation[1] = "│ † │";
                this.rapresentation[2] = "└───┘";
                break;

            case "anything":
                this.rapresentation[0] = "\u001B[35m" + "┌───┐" + "\u001B[0;00m";
                this.rapresentation[1] = "\u001B[35m" + "│ ? │" + "\u001B[0;00m";
                this.rapresentation[2] = "\u001B[35m" + "└───┘" + "\u001B[0;00m";
                break;

            case "1":
                this.rapresentation[0] = "▛▀▀▀▀▀▜";
                this.rapresentation[1] = "▌     ▐";
                this.rapresentation[2] = "▌  •  ▐";
                this.rapresentation[3] = "▌     ▐";
                this.rapresentation[4] = "▙▞▚▄▞▚▟";
                break;

            case "2":
                this.rapresentation[0] = "▛▀▀▀▀▀▜";
                this.rapresentation[1] = "▌  •  ▐";
                this.rapresentation[2] = "▌  •  ▐";
                this.rapresentation[3] = "▌     ▐";
                this.rapresentation[4] = "▙▞▚▄▞▚▟";
                break;

            case "3":
                this.rapresentation[0] = "▛▀▀▀▀▀▜";
                this.rapresentation[1] = "▌  •  ▐";
                this.rapresentation[2] = "▌  •  ▐";
                this.rapresentation[3] = "▌  •  ▐";
                this.rapresentation[4] = "▙▞▚▄▞▚▟";
                break;

            case "leader":
                this.rapresentation[0] = "▛▀▀▀▀▀▜";
                this.rapresentation[1] = "▌     ▐";
                this.rapresentation[2] = "▌     ▐";
                this.rapresentation[3] = "▌     ▐";
                this.rapresentation[4] = "▙▞▚▄▞▚▟";
                break;

            case "cross":
                break;

            default:
                if (this.toString().equals("LEVEL1") || this.toString().equals("LEVEL2") || this.toString().equals("LEVEL3")){
                    rapresentation[0] = "       ";
                    rapresentation[1] = "       ";
                    rapresentation[2] = "       ";
                    rapresentation[3] = "       ";
                    rapresentation[4] = "       ";
                } else{
                    rapresentation[0] = "     ";
                    rapresentation[1] = "     ";
                    rapresentation[2] = "     ";
                }
        }
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public String returnLine(int line){
        return rapresentation[line];
    }
}
