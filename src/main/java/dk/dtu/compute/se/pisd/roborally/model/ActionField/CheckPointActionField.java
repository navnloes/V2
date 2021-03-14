package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class CheckPointActionField extends ActionField {

    int id;

    public CheckPointActionField(Board board, int x, int y, String msg, int id){
        super(board, x, y, msg);
        this.id = id;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public int id(){
        return id;
    }



}
