package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class CheckPointActionField extends ActionField {

    int id;
    /**
     * @author S201192
     *  This method construtcs a gearsactionfield.
     * @param board
     * @param x x is the coordinats of the space.
     * @param y y is the coordinates of the space.
     * @param msg sends a msg to the plauer.
     * @param id is the unique ID for each checkpoint
     */
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
