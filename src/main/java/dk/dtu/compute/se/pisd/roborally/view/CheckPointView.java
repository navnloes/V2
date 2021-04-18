package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @Author S205354, S205353, s205339 (label)
 */


public class CheckPointView extends SpaceView {

    private Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     *
     * @param space is the space the checkPoint is placed
     * @param id is the unique ID for each of the three checkPoints
     */
    public CheckPointView(@NotNull Space space, int id) {
        super(space);

    }

    public void deactivateCP() {
        gc.setFill(Color.GREY);
    }

    public void activateCP() {
        gc.setFill(Color.GOLD);
    }

}