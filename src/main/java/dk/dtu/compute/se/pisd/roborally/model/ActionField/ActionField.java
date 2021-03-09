package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public abstract class ActionField extends Subject {

    public final Board board;
    public final String name;
    public final int x;
    public final int y;
    public final String msg;
    private Player player;

    public ActionField (Board board, String name,int x, int y, String msg){
        this.board = board;
        this.name = name;
        this.x = x;
        this.y = y;
        this.msg = msg;
        player = null;

    }

    public abstract void performFieldAction();

}
