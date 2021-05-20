package it.polimi.ingsw.Client.CLI.Views.LeaderCardView;

import java.util.ArrayList;

public class LeaderCardVisualization {
    public String effect;
    public ArrayList<LeaderCardSymbols> requirements;
    public ArrayList<String> requirementsColor;
    public String effectColor;

    public String[] card;

    public int winnerPoints;

    LeaderCardVisualization(String specialAbility, int winnerPoints, ArrayList<String> requirements, String type) {
        this.effect = specialAbility;
        this.requirements = new ArrayList<>();
        this.requirementsColor = new ArrayList<>();
        this.card = new String[22];
        this.winnerPoints = winnerPoints;

        for (String s : requirements) {
            if(s!=null) {
                if (specialAbility.equals("biggerDeposit"))
                    this.requirements.add(LeaderCardSymbols.valueOf(s));
                if (specialAbility.equals("marketWhiteChange") || specialAbility.equals("costLess")) {
                    this.requirements.add(LeaderCardSymbols.PRODUTCIONLVL1);
                    this.requirementsColor.add(resourceToColorConversion(s));
                }
                if (specialAbility.equals("additionalProduction")) {
                    this.requirements.add(LeaderCardSymbols.PRODUTCIONLVL2);
                    this.requirementsColor.add(resourceToColorConversion(s));
                }
            }
        }

        this.effectColor = resourceToColorConversion(type);
        saveCard(specialAbility);
    }

    LeaderCardVisualization(String in){
        this.card = new String[22];
        this.effect = "nothing";
        saveCard("nothing");
    }

    private String resourceToColorConversion(String resource) {
        switch (resource) {
            case "COIN":
                return ("\u001B[93m");

            case "SHIELD":
                return ("\u001B[36m");

            case "STONE":
                return ("\u001B[1;37m");

            case "SERVANT":
                return ("\u001B[35m");

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

    private void saveCard(String specialAbility) {
        switch (specialAbility) {
            case "biggerDeposit":
                this.card[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
                this.card[1] = "┃        LEADER CARD        ┃";
                this.card[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[3] = "┃                           ┃";
                this.card[4] = "┃           " + requirements.get(0).printLine(0) + "       ┃";
                this.card[5] = "┃           " + requirements.get(0).printLine(1) + "       ┃";
                this.card[6] = "┃       5 x " + requirements.get(0).printLine(2) + "       ┃";
                this.card[7] = "┃           " + requirements.get(0).printLine(3) + "       ┃";
                this.card[8] = "┃           " + requirements.get(0).printLine(4) + "       ┃";
                this.card[9] = "┃                           ┃";
                this.card[10] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[11] = "┃                           ┃";
                this.card[12] = "┃     WINNER POINTS:  " + this.winnerPoints + "     ┃";
                this.card[13] = "┃                           ┃";
                this.card[14] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[15] = "┃                           ┃";
                this.card[16] = "┃        ADD DEPOSIT        ┃";
                this.card[17] = "┃                           ┃";
                this.card[18] = "┃      " + this.effectColor + "┌───┐     " + this.effectColor + "┌───┐      " + "\u001B[0;00m" + "┃";
                this.card[19] = "┃      " + this.effectColor + "│ █ │     " + this.effectColor + "│ █ │      " + "\u001B[0;00m" + "┃";
                this.card[20] = "┃      " + this.effectColor + "└───┘     " + this.effectColor + "└───┘      " + "\u001B[0;00m" + "┃";
                this.card[21] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
                break;

            case "marketWhiteChange":
                this.card[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
                this.card[1] = "┃        LEADER CARD        ┃";
                this.card[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[3] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(0) + "   " + requirementsColor.get(2) + requirements.get(2).printLine(0)+ "   " + "\u001B[0;00m" + "┃";
                this.card[4] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(1) + "   " + requirementsColor.get(2) + requirements.get(2).printLine(1)+ "   " + "\u001B[0;00m" + "┃";
                this.card[5] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(2) + "   " + requirementsColor.get(2) + requirements.get(2).printLine(2)+ "   " + "\u001B[0;00m" + "┃";
                this.card[6] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(3) + "   " + requirementsColor.get(2) + requirements.get(2).printLine(3)+ "   " + "\u001B[0;00m" + "┃";
                this.card[7] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(4) + "   " + requirementsColor.get(2) + requirements.get(2).printLine(4)+ "   " + "\u001B[0;00m" + "┃";
                this.card[8] = "┃                           ┃";
                this.card[9] = "┃      (2)         (1)      ┃";
                this.card[10] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[11] = "┃                           ┃";
                this.card[12] = "┃     WINNER POINTS:  " + this.winnerPoints + "     ┃";
                this.card[13] = "┃                           ┃";
                this.card[14] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[15] = "┃                           ┃";
                this.card[16] = "┃    MARKET WHITE CHANGE    ┃";
                this.card[17] = "┃                           ┃";
                this.card[18] = "┃      ┌───┐     " + this.effectColor + "┌───┐      " + "\u001B[0;00m" + "┃";
                this.card[19] = "┃      │ █ │  =  " + this.effectColor + "│ █ │      " + "\u001B[0;00m" + "┃";
                this.card[20] = "┃      └───┘     " + this.effectColor + "└───┘      " + "\u001B[0;00m" + "┃";
                this.card[21] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
                break;

            case "costLess":
                this.card[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
                this.card[1] = "┃        LEADER CARD        ┃";
                this.card[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[3] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(0) + "   " + requirementsColor.get(1) + requirements.get(1).printLine(0) + "   " + "\u001B[0;00m" + "┃";
                this.card[4] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(1) + "   " + requirementsColor.get(1) + requirements.get(1).printLine(1) + "   " + "\u001B[0;00m" + "┃";
                this.card[5] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(2) + "   " + requirementsColor.get(1) + requirements.get(1).printLine(2) + "   " + "\u001B[0;00m" + "┃";
                this.card[6] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(3) + "   " + requirementsColor.get(1) + requirements.get(1).printLine(3) + "   " + "\u001B[0;00m" + "┃";
                this.card[7] = "┃   " + requirementsColor.get(0) + requirements.get(0).printLine(4) + "   " + requirementsColor.get(1) + requirements.get(1).printLine(4) + "   " + "\u001B[0;00m" + "┃";
                this.card[8] = "┃                           ┃";
                this.card[9] = "┃      (1)         (1)      ┃";
                this.card[10] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[11] = "┃                           ┃";
                this.card[12] = "┃     WINNER POINTS:  " + this.winnerPoints + "     ┃";
                this.card[13] = "┃                           ┃";
                this.card[14] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[15] = "┃                           ┃";
                this.card[16] = "┃      COSTS REDUCTION      ┃";
                this.card[17] = "┃                           ┃";
                this.card[18] = "┃      ╭─────────────╮      ┃";
                this.card[19] = "┃      │ development │      ┃";
                this.card[20] = "┃      │    -1 " + this.effectColor + "█     " + "\u001B[0;00m" + "│      ┃";
                this.card[21] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
                break;

            case "additionalProduction":
                this.card[0] = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
                this.card[1] = "┃        LEADER CARD        ┃";
                this.card[2] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[3] = "┃         " + requirementsColor.get(0) + requirements.get(0).printLine(0) + "         " + "\u001B[0;00m" + "┃";
                this.card[4] = "┃         " + requirementsColor.get(0) + requirements.get(0).printLine(1) + "         " + "\u001B[0;00m" + "┃";
                this.card[5] = "┃         " + requirementsColor.get(0) + requirements.get(0).printLine(2) + "         " + "\u001B[0;00m" + "┃";
                this.card[6] = "┃         " + requirementsColor.get(0) + requirements.get(0).printLine(3) + "         " + "\u001B[0;00m" + "┃";
                this.card[7] = "┃         " + requirementsColor.get(0) + requirements.get(0).printLine(4) + "         " + "\u001B[0;00m" + "┃";
                this.card[8] = "┃                           ┃";
                this.card[9] = "┃            (1)            ┃";
                this.card[10] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[11] = "┃                           ┃";
                this.card[12] = "┃     WINNER POINTS:  " + this.winnerPoints + "     ┃";
                this.card[13] = "┃                           ┃";
                this.card[14] = "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
                this.card[15] = "┃                           ┃";
                this.card[16] = "┃   ADDITIONAL PRODUCTION   ┃";
                this.card[17] = "┃                           ┃";
                this.card[18] = "┃  " + this.effectColor + "┌───┐     " + "\u001B[0;00m" + "┌───┐   ┌───┐  ┃";
                this.card[19] = "┃  " + this.effectColor + "│ █ │" + "\u001B[0;00m" + "  →  │ ? │ + │ † │  ┃";
                this.card[20] = "┃  " + this.effectColor + "└───┘     " + "\u001B[0;00m" + "└───┘   └───┘  ┃";
                this.card[21] = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
                break;

            case "nothing":
                for(int i=0; i<22; i++)
                    this.card[i] = "                             ";
                break;
        }
    }

    public String printCard(int line) {
        return this.card[line];
    }
}

