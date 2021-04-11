package dk.dtu.compute.se.pisd.roborally.model.actionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;


public abstract class ActionField extends Subject {

    public Board board;
    public int x;
    public int y;
    public String msg;

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

    public ActionField(Heading heading){
    }

    public abstract int x();
    public abstract int y();

}
