package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PitFieldAction extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space){
        Player player = space.getPlayer();

        if (player != null){
            CommandCardField[] cards = player.getCards();
            CommandCardField[] program = player.getProgram();
            player.setReboot(true);
            player.spamDamage(2);

            for (int i = 0; i < cards.length; i++){
                player.setCardInvisible(i);
            }
            for (int i = 0; i < program.length; i++){
                player.addDiscardCard(program[i].getCard());
                program[i].setCard(null);
            }


            return true;
        }
        return false;
    }
}
