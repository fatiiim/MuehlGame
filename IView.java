package View;
import Controller.*;
import Model.*;

/**
 * View Interface
 */
public interface IView {
    /**
     * Sets the controller for the view.
     *
     * @param controller The game controller to be set.
     */
    void setController(IController controller );

    /**
     * draw start, which image to show , for game over or start screen
     * @param j the boolean param if should the image over or start shown
     */
    void drawStart(boolean j);
    /**
     * Draw the start screen.
     */
    void drawStart();
    /**
     * Check for the spacebar press to start the game.
     */
    void pressButtonStart();
    /**
     * Draw the game board with stones.
     *
     * @param boardPositions Array of positions representing the game board.
     */
    void drawBoard(Position[] boardPositions);
    /**
     * Draw the outline of the game board.
     */
    void createBoardOutline();
    /**
     * Draw the current player's turn indicator.
     *
     * @param turn The current player's turn.
     */
    void  drawPlayer(Token turn);

    /**
     * Draw the current game state indicator.
     *
     * @param currentState The current state of the game.
     */
    void drawState(State currentState);
    /**
     * Draw a highlight around a stone on hover.
     *
     * @param boardPosition The position of the stone.
     */
    void hoverStone(Position boardPosition);
    /**
     * Display a message on the screen.
     *
     * @param message The message to be displayed.
     *                Should be a String only.
     */
    void displayMessage(String message);
    /**
     * Draw the game over screen.
     */

}
