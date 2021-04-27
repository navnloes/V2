package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space){
        Player player = space.getPlayer();

        if (player != null){
            CommandCardField[] cards = player.getCards();
            CommandCardField[] program = player.getProgram();
            player.setReboot(true);

            for (int i = 0; i < cards.length; i++){
                player.setCardInvisible(i);
            }
            //TODO: eksekver ikke programmeringskort

            return true;
        }
        return false;
    }
}
