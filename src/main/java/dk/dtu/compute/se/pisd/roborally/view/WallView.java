package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

public class WallView extends SpaceView {

    /**
     *
     * @param space is the space the wall is placed
     * @param dir is the direction of the wall
     */
    public WallView(@NotNull Space space, Direction dir) {
        super(space);

    }
}
