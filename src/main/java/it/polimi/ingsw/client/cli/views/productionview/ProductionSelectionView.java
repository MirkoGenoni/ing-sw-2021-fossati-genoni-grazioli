package it.polimi.ingsw.client.cli.views.productionview;

import it.polimi.ingsw.client.cli.views.TotalResourceCounter;
import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the visualization of the production choice given to the user
 *
 * @author Mirko Genoni
 */
public class ProductionSelectionView {
    BaseProduction baseProduction;
    DevelopmentCardView developmentCardBoard;
    AdditionalProductionView additionalProductionView;
    ArrayList<Boolean> activation;
    TotalResourceCounter totalResourceCounter;

    ArrayList<Resource> inputBaseProduction;
    ProductedMaterials prodottoBaseProd;

    boolean useBaseProduction;

    ArrayList<Boolean> leaderActivation;
    ArrayList<Resource> materialLeader;

    ArrayList<Boolean> selectDevelopment;

    //GETTER PER PRODUZIONE BASE

    /**
     *
     * @return true if the user uses the base production
     */
    public boolean isUseBaseProduction() {
        return useBaseProduction;
    }

    /**
     *
     * @return the input to the base production
     */
    public ArrayList<Resource> getInputBaseProduction() {
        return inputBaseProduction;
    }

    /**
     *
     * @return the material producted
     */
    public ProductedMaterials getProdottoBaseProd() {
        return prodottoBaseProd;
    }

    //GETTER PER PRODUZIONE LEADER

    /**
     *
     * @return true in a position if the user wants to activate a specific additional production
     */
    public ArrayList<Boolean> getLeaderActivation() {
        return leaderActivation;
    }

    /**
     *
     * @return the resource requested to the additional production by the user
     */
    public ArrayList<Resource> getMaterialLeader() {
        return materialLeader;
    }

    //GETTER PER DEVELOPMENT

    /**
     *
     * @return true if the user wants to activate the development in that position
     */
    public ArrayList<Boolean> getSelectDevelopment() {
        return selectDevelopment;
    }

    /**
     * Constructor of the class initializes all the data structure
     * @param developmentCardBoard contains all the development card possessed by the player
     * @param additionalProductionView contains all the additional production possessed by the player
     * @param totalResourceCounter contains the counter of all the resources possessed by the player
     */
    public ProductionSelectionView(DevelopmentCardView developmentCardBoard, AdditionalProductionView additionalProductionView , TotalResourceCounter totalResourceCounter){
        this.baseProduction = new BaseProduction();
        this.developmentCardBoard = developmentCardBoard;
        this.additionalProductionView = additionalProductionView;
        this.activation = new ArrayList<>();

        this.inputBaseProduction = new ArrayList<>();

        this.leaderActivation = new ArrayList<>();
        this.leaderActivation = new ArrayList<>();

        this.selectDevelopment = new ArrayList<>();

        this.totalResourceCounter = totalResourceCounter;
    }

    /**
     * Handles the user input for the production selection (base, leader, development)
     * @param request contains one of the production available
     * @return true if the player activates the production requested
     */
    public boolean activationSelection(String request) {
        Scanner in = new Scanner(System.in);
        String input;
        boolean tmp;

        if (request.equals("base")) {
            inputBaseProduction.clear();
            inputBaseProduction.add(null);
            inputBaseProduction.add(null);
            inputBaseProduction.add(null);

            while (true) {
                printSelection("base");

                input = in.nextLine();
                input = input.toLowerCase();

                if (input.equals("yes")) {
                    this.useBaseProduction = true;
                    this.activation.add(true);
                    baseProduction.startBaseProduction();

                    if (!baseProduction.isTurnEnd)
                        return false;
                    else {
                        inputBaseProduction = baseProduction.getResources();
                        prodottoBaseProd = ProductedMaterials.valueOf(inputBaseProduction.get(2).name());
                        return true;
                    }
                } else if(input.equals("no")) {
                    this.activation.add(false);
                    return true;
                }
            }
        }

        else if(request.equals("leader")){
            while(true){
                printSelection("leader");

                input = in.nextLine();
                input = input.toLowerCase();

                if(input.equals("yes")) {
                    this.activation.add(true);
                    additionalProductionView.startAdditionalProductionView();

                    if(!additionalProductionView.isTurnEnd())
                        return false;
                    else{
                        this.leaderActivation = additionalProductionView.getActivation();
                        this.materialLeader = additionalProductionView.getRequested();
                        return true;
                    }
                }
                else if(input.equals("no")){
                    this.activation.add(false);
                    return true;
                }
            }
        }

        else if(request.equals("development")){
            selectDevelopment.clear();
            selectDevelopment.add(false);
            selectDevelopment.add(false);
            selectDevelopment.add(false);

            while(true) {
                printSelection("development");

                input = in.nextLine();
                input = input.toLowerCase();

                if (input.equals("yes")) {
                    this.activation.add(true);
                    developmentCardBoard.startProductionCardBoardView();

                    if (!developmentCardBoard.isTurnEnd())
                        return false;
                    else {
                        this.selectDevelopment = developmentCardBoard.getActivation();
                        return true;
                    }
                } else if (input.equals("no")) {
                    this.activation.add(false);
                    return true;
                }
            }
        }

        else{
            return false;
        }
    }

    /**
     * Prints the view for production selection
     * @param request is the production that is asked to the player if he wants to activate
     */
    private void printSelection(String request){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.print("                                       ┌───────────────────────────────────────────┐                                       \n" +
                "                                       │SELECT THE DEVELOPMENT YOU WANT TO ACTIVATE│\n" +
                "                                       └───────────────────────────────────────────┘\n\n\n");
        for(int i=0; i<3; i++)
            System.out.println(totalResourceCounter.returnLine(i));
        System.out.print("\n" +
                "\n" +
                "                                      -> DO YOU WANT TO ACTIVATE THE BASE PRODUCTION?\n" + "\n");
        System.out.print("                                                             ");

        if(request.equals("leader") || request.equals("development")) {

            if(this.activation.get(0))
                System.out.println("YES");
            else
                System.out.println("NO");

            System.out.println("\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                                      -> DO YOU WANT TO ACTIVATE A LEADER PRODUCTION?\n" + " \n");
            System.out.print("                                                             ");
        }

        if(request.equals("development")) {
            if(this.activation.get(1))
                System.out.print("YES");
            else
                System.out.print("NO");

            System.out.println("\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                                 -> DO YOU WANT TO ACTIVATE ONE OF YOUR DEVELOPMENT CARD?\n" + "\n");
            System.out.print("                                                             ");
        }
    }
}
