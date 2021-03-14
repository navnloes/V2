package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @Author S205354, S205353 (label)
 */

public class CheckPointView extends SpaceView {

    private Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    public CheckPointView(@NotNull Space space, int id) {
        super(space);

        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLUE);
        gc.setFill(Color.GOLD);

            gc.fillOval(12.5, 12.5, 49, 49);
            gc.strokeOval(12.5, 12.5, 50, 50);
            gc.strokeOval(12.5, 12.5, 51, 51);

        this.getChildren().add(canvas);

        Label label = new Label((id+1) + "");
        this.getChildren().add(label);

    }
    public void deactivateCP() {
        gc.setFill(Color.GREY);
    }

    public void activateCP() {
        gc.setFill(Color.GOLD);
    }

}
