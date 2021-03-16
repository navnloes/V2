package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class ConveyorBeltView extends SpaceView {

    /**
     * @author S205354
     * @param space
     * @param heading
     */

    public ConveyorBeltView(@NotNull Space space, Heading heading) {
        super(space);

        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.DARKGREEN);

        if(heading == Heading.NORTH){
            gc.strokeLine(2, SPACE_HEIGHT - 2,
                    SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
        } else if (heading == Heading.EAST){
            gc.strokeLine(2, 0,
                    0, SPACE_HEIGHT - 2);
        } else if (heading == Heading.SOUTH){
            gc.strokeLine(2, 0,
                    0, SPACE_HEIGHT - 2);
        } else if (heading == Heading.WEST){
            gc.strokeLine(2, 0,
                    0, SPACE_HEIGHT - 2);
        }
        this.getChildren().add(canvas);

    }
}
