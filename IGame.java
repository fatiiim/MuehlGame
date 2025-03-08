package Model;

/**
 * Muehle Interface
 */
public interface IGame {
    /**
     * Starts a new game by initializing players and setting the initial turn to white.
     */
    void startNewGame();

    /**
     * Gets the array of board positions.
     *
     * @return The array of board positions.
     */
    Position[] getBoardPositions();
    /**
     * Executes the first phase of the game, calculating available pieces, placing them on the board,
     * and checking for mill combinations.
     * The first phase ends when there are no stones for both players left, when they have placed all of them of the Board
     * @param i    The position index.
     * @param turn The player's turn.
     */
    void firstPhase(int i,Token turn);

    /**
     *
     *  Executes the second phase of the game, involving hover, move, and remove steps based on the game state.
     * @param i The position index.
     * @param turn The player's turn.
     */
    void secondPhase(int i,Token turn);

    /**
     * Executes the third phase of the game, involving hover, move, and remove steps with additional rules.
     * @param i The position index.
     * @param turn  The player's turn.
     */
    void thirdPhase(int i,Token turn);
    /**
     * Resets the game to its initial state, clearing the board and resetting player information.
     */
    void reset();
    /**
     * Starts a thread to pause the program.
     */
    void startThread();
    /**
     * Checks if the game is over by examining the result of the checkWin() method.
     *
     * @return True if the game is over; otherwise, false.
     */
    boolean gameOver();

    /**
     * skips the Turn when there is mill, because the same Player needs to continue playing
     * @return return false or true
     */
    boolean getSkipTurn();
    /**
     * Gets the current turn (player's color).
     *
     * @return The current turn (player's color).
     */
    Token getTurn();
    /**
     * Retrieves Player 2. white
     * @return Player 2 instance
     */
    Player getPlayer2();
    /**
     * Retrieves Player 1. white
     * @return Player 1 instance
     */
    Player getPlayer1();
}
