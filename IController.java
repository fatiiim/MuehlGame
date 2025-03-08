package Controller;
import Model.IGame;
import View.IView;

/**
 * Controller Interface
 */
public interface IController {
    /**
     *Reference to the game model
     * @param model model object
     */
    void setModel(IGame model);

    /**
     * Reference to the game view
     * @param view view object
     */
    void setView(IView view);
    /**
     * Handles the logic for the next frame of the game based on its current state.
     */
    void nextFrame() ;
    /**
     * Sets the game state to START.
     */
    void GameStateStart() ;
    /**
     * Sets the game state to the specified state.
     *
     * @param currentState The state to set.
     */
    void setStatus(State currentState);
    /**
     * Sets the game state to PLAYING_1 and starts a new game in the model.
     */
    void GameStatePlaying();
    /**
     * Handles user input based on mouse coordinates.
     *
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     */
    void userInput(int mouseX, int mouseY);

}
