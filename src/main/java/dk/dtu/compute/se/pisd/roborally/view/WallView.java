package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Wall;
import dk.dtu.compute.se.pisd.roborally.model.WallCollection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

enum Direction { HORIZONTAL, VERTICAL}

public class WallView extends SpaceView {

    //TODO: vægge
    public WallView(@NotNull Space space, Direction dir) {
        super(space);
        List<Wall> walls = WallCollection.getInstance().getMyCollection();

        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(10);
        gc.setLineCap(StrokeLineCap.ROUND);
        //husk at tilføje variabel i for kristoffer
        if(dir == Direction.HORIZONTAL){
            gc.strokeLine(2, SPACE_HEIGHT - 2,
                    SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
        } else {
            gc.strokeLine(2, 0,
                    0, SPACE_HEIGHT - 2);
        }
        this.getChildren().add(canvas);

        /*
        if (i == 1) {
            gc.strokeLine(2, SPACE_HEIGHT -2,
                    SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
            this.getChildren().add(canvas); }

        else if (i == 0) {
            gc.strokeLine(2, SPACE_HEIGHT * 0,
                    SPACE_WIDTH * 0, SPACE_HEIGHT - 2);
            this.getChildren().add(canvas); }


    }
    */

    }
}