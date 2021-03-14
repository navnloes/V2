package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class CheckPointView extends SpaceView {

    public CheckPointView(@NotNull Space space) {
        super(space);

        this.setStyle("-fx-background-color: gold;");

    }
}
