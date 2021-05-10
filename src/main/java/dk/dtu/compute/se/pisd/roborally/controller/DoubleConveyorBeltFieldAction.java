package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class DoubleConveyorBeltFieldAction extends FieldAction{

    /**
     *
     * @Author S205354
     * */

    Heading heading;

    public DoubleConveyorBeltFieldAction(){
    }

    public Heading getHeading(){
        return heading;
    }

    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        if (space != null && heading != null) {
            Player player = space.getPlayer();
            if (player != null) {
            Heading heading = player.getHeading();
            player.setHeading(this.heading);
            gameController.fastForward(player);
            player.setHeading(heading);}
            return true;
        } else {
            return false;
        }
    }
}
