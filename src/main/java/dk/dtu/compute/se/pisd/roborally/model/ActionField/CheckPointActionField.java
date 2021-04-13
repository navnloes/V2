package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckPointActionField extends ActionField {

    int id;
    /**
     * @author S201192
     */
    public CheckPointActionField(){

    }

    @Override
    public void doAction(GameController gameController, Space space) {

    }

    public int id(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }



}
