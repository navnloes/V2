package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class ConveyorBeltActionField extends ActionField {

    Heading heading;

    /**
     * @author S201192 & S205354
     *  This method construtcs a gearsactionfield.
     * @param board
     * @param x x is the coordinats of the space.
     * @param y y is the coordinates of the space.
     * @param msg sends a msg to the plauer.
     * @param heading is the heading of the player.
     */

    public ConveyorBeltActionField(Board board, int x, int y, String msg, Heading heading){
        super(board, x, y, msg);
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
