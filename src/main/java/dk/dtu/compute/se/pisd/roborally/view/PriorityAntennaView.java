package dk.dtu.compute.se.pisd.roborally.view;


import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class PriorityAntennaView extends SpaceView {

    private Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    public PriorityAntennaView(@NotNull Space space) {
        super(space);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLUE);
        gc.setFill(Color.GREEN);
        gc.fillOval(7.5, 7.5, 49, 49);
        gc.strokeOval(7.5, 7.5, 50, 50);
        gc.strokeOval(7.5, 7.5, 51, 51);
        this.getChildren().add(canvas);
        canvas.toBack();
    }


}
