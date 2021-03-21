package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;


public class GearsView extends SpaceView {

    /**
     * @author S205354, s205339
     * @param space is the space the gear is placed
     * @param direction is the direction the gear will turn the player
     */
    public GearsView(@NotNull Space space, Direction direction) {
        super(space);

        Polygon gearPart1 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
        Polygon gearPart2 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
        Polygon gearPart3 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
        gearPart1.setFill(Color.DARKGREY);
        gearPart2.setFill(Color.DARKGREY);
        gearPart3.setFill(Color.DARKGREY);

        gearPart1.setRotate(120);
        gearPart2.setRotate(240);

        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        if (direction == direction.LEFT) {
            gc.setFill(Color.RED);}
        else {
            gc.setFill(Color.GREEN);
        }
        gc.fillOval(17.5, 17.5, 40, 40);

        Label label = new Label(direction.toString());

        this.getChildren().add(canvas);
        canvas.toBack();

        this.getChildren().add(gearPart1);
        gearPart1.toBack();

        this.getChildren().add(gearPart2);
        gearPart2.toBack();

        this.getChildren().add(gearPart3);
        gearPart3.toBack();

        this.getChildren().add(label);
    }
}
