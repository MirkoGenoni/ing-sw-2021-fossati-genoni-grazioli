package it.polimi.ingsw.model.lorenzo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Lorenzo il Magnifico to manage Lorenzo Player
 * @author davide
 */
public class LorenzoIlMagnifico {

     ArrayList<SoloAction> soloActionTokens;

    public LorenzoIlMagnifico() {
        soloActionTokens = new ArrayList<>();

        Collections.addAll(soloActionTokens, SoloAction.values());
        Collections.shuffle(soloActionTokens);

    }


    /**
     * Getter of SoloAction Collection
     * @return the Array of SoloActionToken
     */
    public ArrayList<SoloAction> getSoloActionTokens() {
        return new ArrayList<>(soloActionTokens);
    }


    /**
     * Method to draw a token for the Lorenzo action
     * @return the drew token from the collection
     */
    public SoloAction getToken(){

        SoloAction tookToken = soloActionTokens.get(0);

        if (tookToken.name().equals(SoloAction.MOVESHUFFLE.name()))
            Collections.shuffle(soloActionTokens);

        else {
            soloActionTokens.remove(0); //remove from head
            soloActionTokens.add(tookToken); //add on tail
        }

        return tookToken;

    }

}
