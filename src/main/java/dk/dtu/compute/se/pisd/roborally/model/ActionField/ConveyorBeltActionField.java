package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class ConveyorBeltActionField extends ActionField {

    Heading heading;

    /**
     * @author S201192 & S205354
     *  This method construtcs a gearsactionfield.
     */

    public ConveyorBeltActionField(){
    }

    public Heading getHeading(){
        return heading;
    }

    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public void doAction(GameController gameController, Space space) {

    }
}
