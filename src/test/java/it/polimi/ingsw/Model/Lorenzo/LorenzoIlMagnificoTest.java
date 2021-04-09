package it.polimi.ingsw.Model.Lorenzo;

import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LorenzoIlMagnificoTest {

    LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico();


    @Test
    public void getSoloActionToken(){
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionToken();

        for(int i=0; i < lorenzo.getSoloActionToken().size(); i++)
            assertEquals(lorenzo.getSoloActionToken().get(i), actionList.get(i));
    }


    @Test
    public void getToken() {
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionToken();


        SoloAction action = lorenzo.getToken();

        if (action.name() != SoloAction.MOVESHUFFLE.name()){

            assertEquals(action.name(), lorenzo.getSoloActionToken().get(actionList.size()-1).name());

        }
        else {

            assertTrue(lorenzo.getSoloActionToken().size() == actionList.size()); //same size

            boolean finded = false;
            for (int i = 0; i < actionList.size(); i++) {

                for (SoloAction soloAction : SoloAction.values()) {
                    if (soloAction.name() == lorenzo.getSoloActionToken().get(i).name()) { //same values at beginning and at the end
                        finded = true;
                        break;
                    }
                }
                assertTrue(finded);
                finded = false;

            }

        }
    }
}