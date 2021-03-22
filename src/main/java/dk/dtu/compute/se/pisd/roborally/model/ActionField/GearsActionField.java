package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class GearsActionField extends ActionField {

    /**
     * @author S205354
     */

    Direction direction;

    /**
     * @author S201192
     *  This method constructs a GearsActionfield.
     * @param board is the specific board which contains spaces.
     * @param x x is the coordinates of the space.
     * @param y y is the coordinates of the space.
     * @param msg sends a msg to the plauer.
     * @param direction gives the direction of the player.
     */
    public GearsActionField(Board board, int x, int y, String msg, Direction direction){
        super(board, x, y, msg);
        this.direction = direction;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public Direction getDirection(){
        return direction;
    }





}
