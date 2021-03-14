package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;


public class CheckPointView extends SpaceView {

    public CheckPointView(@NotNull Space space, int id) {
        super(space);

        this.setStyle("-fx-background-color: gold;");
        Label label = new Label((id+1) + "");
        this.getChildren().add(label);

    }
}
