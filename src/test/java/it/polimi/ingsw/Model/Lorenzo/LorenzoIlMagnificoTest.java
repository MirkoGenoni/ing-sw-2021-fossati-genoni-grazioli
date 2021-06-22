package it.polimi.ingsw.Model.Lorenzo;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LorenzoIlMagnificoTest {

    LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico();


    @Test
    public void getSoloActionToken(){
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionTokens();

        for(int i = 0; i < lorenzo.getSoloActionTokens().size(); i++)
            assertEquals(lorenzo.getSoloActionTokens().get(i), actionList.get(i));
    }


    @Test
    public void getToken() {

        for (int i=0; i<6;i++) { //probability of 1/6^3 of three moveshuffle moves

            ArrayList<SoloAction> actionList = lorenzo.getSoloActionTokens();
            SoloAction action = lorenzo.getToken();


            if (!action.name().equals(SoloAction.MOVESHUFFLE.name())) {

                assertEquals(action.name(), lorenzo.getSoloActionTokens().get(actionList.size() - 1).name());

            } else {

                assertTrue(lorenzo.getSoloActionTokens().size() == actionList.size()); //same size
                //assertTrue(lorenzo.getSoloActionToken().size()==6);

                boolean finded = false;
                for (int j = 0; j < actionList.size(); j++) {

                    for (SoloAction soloAction : SoloAction.values()) {
                        if (soloAction.name().equals(lorenzo.getSoloActionTokens().get(i).name())) { //same values at beginning and at the end
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
}