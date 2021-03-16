package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;


public class ConveyorBeltView extends SpaceView {

    /**
     * @author S205354
     * @param space
     * @param heading
     */

    public ConveyorBeltView(@NotNull Space space, Heading heading) {
        super(space);

        Polygon arrow = new Polygon(0.0, 0.0, 15.0, 40, 30.0, 0.0);
            arrow.setFill(Color.DEEPSKYBLUE);
            Label label = new Label("Conveyor");

        if(heading == Heading.NORTH){
            arrow.setRotate(180);
        } else if (heading == Heading.EAST){
            arrow.setRotate(270);
        } else if (heading == Heading.SOUTH){
            arrow.setRotate(0);
        } else if (heading == Heading.WEST){
            arrow.setRotate(90);
        }

        this.getChildren().add(arrow);
        arrow.toBack();

        this.getChildren().add(label);
    }
}
