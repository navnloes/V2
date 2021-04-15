package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author s205353
 * This class models the playerActions that a robot/player can have
 */
public abstract class PlayerAction {

    /**
     * Executes the player action for a given player.
     *
     * @param gameController the gameController of the respective game
     * @param player the player whose action should be executed
     * @return whether the action was successfully executed
     */
    public abstract boolean doAction(GameController gameController, Player player);

}
