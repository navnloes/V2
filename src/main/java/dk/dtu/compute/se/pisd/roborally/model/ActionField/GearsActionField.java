package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class GearsActionField extends ActionField {

    /**
     * @author S205354
     */

    Direction direction;


    public GearsActionField(){

    }

    @Override
    public void doAction(GameController gameController, Space space) {

    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }



}
