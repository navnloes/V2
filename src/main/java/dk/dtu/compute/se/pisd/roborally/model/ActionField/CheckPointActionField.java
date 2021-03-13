package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class CheckPointActionField extends ActionField {

    public CheckPointActionField(Board board, String name, int x, int y, String msg){
        super(board, name, x, y, msg);
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    @Override
    public void performFieldAction() {

    }
}
