package it.polimi.ingsw.Model.Lorenzo;

import java.util.ArrayList;
import java.util.Collections;

public class LorenzoIlMagnifico {

     ArrayList<SoloAction> soloActionToken;

    public LorenzoIlMagnifico() {
        soloActionToken = new ArrayList<>();

        Collections.addAll(soloActionToken, SoloAction.values());
        Collections.shuffle(soloActionToken);

    }


    public ArrayList<SoloAction> getSoloActionToken() {
        return new ArrayList<>(soloActionToken);
    }



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
