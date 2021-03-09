package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

public class WallView extends SpaceView{

    //TODO: vægge
    public WallView(@NotNull Space space) {
        super(space);
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        gc.setLineCap(StrokeLineCap.ROUND);

        gc.strokeLine(2, SPACE_HEIGHT-2,
                SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
        this.getChildren().add(canvas);
    }

}
