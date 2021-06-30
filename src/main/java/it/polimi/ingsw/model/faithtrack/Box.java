package it.polimi.ingsw.model.faithtrack;
/**
 * Class Box
 * @author davide grazioli
 */
public class Box {

    private final int pointPos;
    private boolean popeSpace;

    /**
     * constructor of the class
     * @param pointPos point that the box grant
     */
    public Box(int pointPos) {
        this.pointPos = pointPos;
        this.popeSpace = false;
    }

    /**
     *
     * @return the point that a box grant
     */
    public int getPointPosition() {
        return pointPos;
    }

    /**
     *
     * @return true if a box is a pope space
     */
    public boolean getPopeSpace() {              //default --> isPopeSpace
        return popeSpace;
    }

    /**
     *
     * @param popeSpace boolean to set the popespace of a box
     */
    public void setPopeSpace(boolean popeSpace) {
        this.popeSpace = popeSpace;
    }

}

