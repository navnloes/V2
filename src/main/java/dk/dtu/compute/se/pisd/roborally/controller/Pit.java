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
            CommandCardField[] program = player.getProgram();
            CommandCardField[] commandCards = player.getCards();
            player.setReboot(true);

            for (int i = 0;i < commandCards.length; i++){
                commandCards[i].setVisible(false);
            }

            for (int i = 0;i < program.length; i++){
                program[i].setVisible(false);
            }

            return true;
        }
        return false;
    }
}
