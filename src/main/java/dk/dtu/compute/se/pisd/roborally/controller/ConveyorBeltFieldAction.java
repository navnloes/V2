package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class ConveyorBeltFieldAction extends FieldAction{

    Heading heading;

    public ConveyorBeltFieldAction(){
        //TODO:
    }

    public Heading getHeading(){
        return heading;
    }

    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        player.setHeading(this.heading);
        return false;
    }
}
