package dk.dtu.compute.se.pisd.roborally.model.ActionField;

public class CheckPointActionField {

    private final int x1;
    private final int y1;

    public CheckPointActionField(int x1, int y1){
        this.x1 = x1;
        this.y1 = y1;
    }

    public int x1(){
        return x1;
    }

    public int x2(){
        return  y1;
    }

}
