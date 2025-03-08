import Controller.*;
import Model.*;
import View.*;
import processing.core.*;

/**
 * The main of the game
 */
public class Main {
    public static void main(String[] args) {
        final int width = 850;
        final int height = 850;
        var model = new MuehleGame();
        var controller = new GameController();
        var view = new GameView(width,height);

        // Connect M, V and C
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        // Starts the processing application
        PApplet.runSketch(new String[]{"View"}, view);
    }}