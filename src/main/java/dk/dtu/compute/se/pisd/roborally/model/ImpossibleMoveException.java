package dk.dtu.compute.se.pisd.roborally.model;

public class ImpossibleMoveException extends Exception {

    private Player player;
    private Space space;
    private Heading heading;

    /**
     * This is the constructor of ImpossibleMoveException
     * @param player Player player
     * @param space Space space
     * @param heading Heading heading
     */
    public ImpossibleMoveException(
            Player player, Space space, Heading heading) {
        super("Move impossible");
        this.player = player;
        this.space = space;
        this.heading = heading;

    }

}
