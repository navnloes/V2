package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public abstract class ActionField extends Subject {

    public final Board board;
    public final int x;
    public final int y;
    public final String msg;

    /**
     *
     * @param board
     * @param x x is space x coordinate
     * @param y y is space y coordinate
     * @param msg is a message that informs you about, the object you landed on
     */
    public ActionField (Board board, int x, int y, String msg){
        this.board = board;
        this.x = x;
        this.y = y;
        this.msg = msg;

    }

    public abstract int x();
    public abstract int y();

}
