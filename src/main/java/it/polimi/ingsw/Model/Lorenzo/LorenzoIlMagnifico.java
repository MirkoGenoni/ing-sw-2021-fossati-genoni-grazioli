package it.polimi.ingsw.Model.Lorenzo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Lorenzo il Magnifico to manage Lorenzo Player
 * @author davide
 */
public class LorenzoIlMagnifico {

     ArrayList<SoloAction> soloActionToken;

    public LorenzoIlMagnifico() {
        soloActionToken = new ArrayList<>();

        Collections.addAll(soloActionToken, SoloAction.values());
        Collections.shuffle(soloActionToken);

    }


    /**
     * Getter of SoloAction Collection
     * @return the Array of SoloActionToken
     */
    public ArrayList<SoloAction> getSoloActionToken() {
        return new ArrayList<>(soloActionToken);
    }


    /**
     * Method to draw a token for the Lorenzo action
     * @return the drew token from the collection
     */
    public SoloAction getToken(){

        SoloAction tookToken = soloActionToken.get(0);

        if (tookToken.name() == SoloAction.MOVESHUFFLE.name())
            Collections.shuffle(soloActionToken);

        else {
            soloActionToken.remove(0); //remove from head
            soloActionToken.add(tookToken); //add on tail
        }

        return tookToken;

    }

}
