package dk.dtu.compute.se.pisd.roborally.model.actionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class ConveyorBeltActionField extends ActionField {

    /**
     * @author S205354
     */

    Heading heading;

    /**
     * @author S201192
     *  This method construtcs a gearsactionfield.
     * @param board
     * @param x x is the coordinats of the space.
     * @param y y is the coordinates of the space.
     * @param heading is the heading of the player.
     */

    public ConveyorBeltActionField(Board board, int x, int y, Heading heading){
        super(board, x, y,"");
        this.heading = heading;
    }

    public ConveyorBeltActionField(Heading heading){
        super(heading);
        this.heading = heading;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public Heading getHeading(){
        return heading;
    }

}
