package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Direction;

public class GearsActionField extends ActionField {

    /**
     * @author S205354
     */

    Direction direction;

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
