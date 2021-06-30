package it.polimi.ingsw.model.lorenzo;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * this class tests the LorenzoIlMagnifico class and its behaviour
 * These test uses an ArrayList of DevelopmentCards created from the DevelopmentCardTest.json to create the DevelopmentCard.
 * @author davide grazioli
 */
public class LorenzoIlMagnificoTest {

    LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico();

    /**
     * This test checks the right initialization of the tokens for Lorenzo.
     */
    @Test
    public void getSoloActionToken(){
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionTokens();

        for(int i = 0; i < lorenzo.getSoloActionTokens().size(); i++)
            assertEquals(lorenzo.getSoloActionTokens().get(i), actionList.get(i));
    }

    /**
     * This test checks the right behaviour of the drawing a token. When i take the MOVESHUFFLE token
     * i have to shuffle all of the tokens otherwise i have to temporary discard them (for semplicity
     * put at the end of the list)
     */
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