package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.view.SpaceView.SPACE_HEIGHT;
import static dk.dtu.compute.se.pisd.roborally.view.SpaceView.SPACE_WIDTH;

public class StopWatchView extends SpaceView{
    public StopWatchView(@NotNull Space space, int time) {
        super(space);

        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(10);
        this.getChildren().add(canvas);

        Label label = new Label((time) + "");
        this.getChildren().add(label);

    }
}
