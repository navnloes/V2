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

    }

    public void deactivateCP() {
        gc.setFill(Color.GREY);
    }

    public void activateCP() {
        gc.setFill(Color.GOLD);
    }

}

/* DO NOT FUCKING DELETE. THIS IS FOR GREYING OUT. Tal med Kris, hvis der er spørgsmål.

        for (int i = 0; i >= space.getPlayer().getCheckPointArray().length; i++) {

            CheckPointView checkPointView = new CheckPointView(space,i);

            if (space.getPlayer().getCheckPointArray(i)) {
                checkPointView.deactivateCP();
            }
            else if (!space.getPlayer().getCheckPointArray(i)) {
                checkPointView.activateCP();
            }
        }
 */