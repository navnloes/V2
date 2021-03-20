package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class ConveyorBeltActionField extends ActionField {

    /**
     * @author S205354
     */

    Heading heading;

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
