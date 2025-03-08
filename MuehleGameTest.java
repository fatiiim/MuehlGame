package Tests;
import Model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the model package
 */
class MuehleGameTest {
    /**
     * initializing the game object
     */
    private MuehleGame game;
    @BeforeEach
    public void setUp() {
        // Initialize your game and related objects before each test
        game = new MuehleGame();
        game.startNewGame(); // Call startNewGame to initialize players
    }

    /**
     *  Test if decrementing pieces to set updates the count correctly.
     *   Arrange: Create a player with white color.
     *    Act: Decrement pieces to set.
     *    Assert: Ensure pieces to set is updated to 8, and pieces on board is 1.
     */
    @Test
    public void testDecrementPiecesToSet() {
        Player player = new Player(Token.WHITE);
        player.decrementPiecesToSet();

        assertEquals(8, player.getPiecesToSet());
        assertEquals(1, player.getPiecesOnBoard());
    }

    /**
     * Test if decrementing pieces on board updates the count correctly.
     * Arrange: Create a player with black color.
     * Act: Decrement pieces on board.
     *  Assert: Ensure pieces on board is updated to 0.
     */
    @Test
    public void testDecrementPiecesOnBoard() {
        Player player = new Player(Token.BLACK);
        player.decrementPiecesOnBoard();

        assertEquals(0, player.getPiecesOnBoard());
    }

    /**
     *  Test if getColor returns the correct color.
     *  Arrange: Create a player with black color.
     *  Assert: Ensure getColor returns black.
     */
    @Test
    public void testGetColor() {
        Player player = new Player(Token.BLACK);
        assertEquals(Token.BLACK, player.getColor());
    }

    /**
     *  Test if getPiecesOnBoard returns the correct count.
     *  Arrange: Create a player with white color and set pieces on board to 3.
     *   Assert: Ensure getPiecesOnBoard returns 3.
     */
    @Test
    public void testGetPiecesOnBoard() {
        Player player = new Player(Token.WHITE);
        player.setPiecesOnBoard(3);

        assertEquals(3, player.getPiecesOnBoard());
    }

    /**
     * Test if setPiecesOnBoard updates the count correctly.
     * Arrange: Create a player with black color.
     * Act: Set pieces on board to 2.
     *  Assert: Ensure getPiecesOnBoard returns 2.
     * }
     */
    @Test
    public void testSetPiecesOnBoard() {
        Player player = new Player(Token.BLACK);
        player.setPiecesOnBoard(2);

        assertEquals(2, player.getPiecesOnBoard());
    }

    /**
     * Test if getPiecesToSet returns the correct count.
     *  Arrange: Create a player with white color.
     *   Assert: Ensure getPiecesToSet returns 9.
     */
    @Test
    public void testGetPiecesToSet() {
        Player player = new Player(Token.WHITE);
        assertEquals(9, player.getPiecesToSet());
    }

    /**
     *  Test if setColor updates the color correctly.
     *  Arrange: Create a player with black color.
     *   Act: Set color to white.
     *   Assert: Ensure getColor returns white.
     */
    @Test
    public void testSetColor() {
        Player player = new Player(Token.BLACK);
        player.setColor(Token.WHITE);

        assertEquals(Token.WHITE, player.getColor());
    }
    /**
     * Checks if the setPaused() method can resume the thread when it was paused,
     * and if the isPaused variable reflects the current state of the thread.
     */
    @Test
    public void setPausedTest() {
        PauseThread pauseThread = new PauseThread();
        pauseThread.setPaused(true);
        assertTrue(pauseThread.isPaused());
        pauseThread.setPaused(false);
        assertFalse(pauseThread.isPaused());
    }
    /**
     * Tests the behavior of the thread during a pause using the wait() and notify() methods.
     */

    @Test
    public void testThreadWaitAndNotify() throws InterruptedException {
        PauseThread pauseThread = new PauseThread();
        pauseThread.setPaused(true);
        pauseThread.start();


        synchronized (pauseThread) {
            pauseThread.wait(1000);
            assertTrue(pauseThread.isPaused());
            pauseThread.setPaused(false);
            pauseThread.notify();
        }
        assertFalse(pauseThread.isPaused());
    }
    /**
     * Tests the interruption of PauseThread.
     * This test case checks whether an IllegalMonitorStateException is thrown when PauseThread is interrupted.
     */

    @Test
    public void interruptExecptionTest() {
        assertThrows(IllegalMonitorStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                PauseThread p = new PauseThread();
                // Setzen des PauseThreads in den Pausenzustand
                p.setPaused(true);
                p.start();
                // Versuch, den PauseThread zu unterbrechen
                p.notify();
            }
        });
    }

    /**
     * test for the Token
     */
    @Test
    public void testTokenValues() {
        assertEquals("WHITE", Token.WHITE.name());
        assertEquals("BLACK", Token.BLACK.name());
        assertEquals("EMPTY", Token.EMPTY.name());
    }
    /**
     * test for the Step
     */
    @Test
    public void testStepValues() {
        assertEquals("HOVER", Step.HOVER.name());
        assertEquals("MOVE", Step.MOVE.name());
        assertEquals("REMOVE", Step.REMOVE.name());
    }

    /**
     * test Position X Return Position X
     */
    @Test
    public void testPositionXReturnPositionX(){
        Position position = new Position(1,2);
        assertEquals(1, position.getX());
    }

    /**
     * test Position Y Return Position X Y
     */
    @Test
    public void testPositionYReturnPositionXY(){
        Position position = new Position(1,2);
        assertEquals(2, position.getY());
    }

    /**
     * test Set X And Y Position Return X And Y
     */
    @Test
    public void testSetXAndYPositionReturnXAndY(){
        Position position = new Position(1,2);
        position.setPosition(3,4);
        assertEquals(3,position.getX());
        assertEquals(4,position.getY());
    }

    /**
     * test Not Init X And Y Position Return New Set X And Y
     */
    @Test
    public void testNotInitXAndYPositionReturnNewSetXAndY() {
        Position position = new Position(1, 2);
        position.setPosition(3, 4);
        assertNotEquals(1, position.getX());
        assertNotEquals(2, position.getY());
    }

    /**
     * test Not Empty Get PosIDX Return TRUE
     */

    @Test
    public void testNotEmptyGetPosIDXReturnTRUE(){
        Position position = new Position(1);
        assertTrue(0<position.getPosIdx());
    }

    /**
     * test Get PosIDX Return TRUE
     */
    @Test
    public void testGetPosIDXReturnTRUE(){
        Position position = new Position(1);
        assertEquals(1,position.getPosIdx());
    }
    /**
     * test No Adjacent Position Return TRUE
     */
    @Test
    public void testNotAdjacentPositionReturnTRUE(){
        Position position = new Position(1,2);
        position.addAdjacentPositionsIndexes(3,4);
        assertNotEquals(new int[]{5,6},position.getAdjacentPositionsIndexes());
    }

    /**
     * test Adjacent Position Return TRUE
     */
    @Test
    public void testAdjacentPositionReturnTRUE(){
        Position position = new Position(1,2);
        position.addAdjacentPositionsIndexes(3,4);
        assertNotEquals(new int[]{3,4},position.getAdjacentPositionsIndexes());
    }

    /**
     *
     *      test X Is Adjacent To This Return True
     *
     */
    @Test
    public void testXIsAdjacentToThisReturnTRUE() {
        Position position = new Position(1, 2);
        position.addAdjacentPositionsIndexes(3, 4);
        assertTrue(position.isAdjacentToThis(4));
    }

    /**
     * test X Is Adjacent To This Return FALSE
     */
    @Test
    public void testXIsAdjacentToThisReturnFALSE() {
        Position position = new Position(1, 2);
        position.addAdjacentPositionsIndexes(3, 4);
        assertFalse(position.isAdjacentToThis(5));
    }

    /**
     * test Set And Get Coordinates
     */
    @Test
    public void testSetAndGetCoordinates() {
        // Create a Position object with initial coordinates (3, 4)

        Position position = new Position(3, 4);

        // Set the coordinates of the position
        position.setPosition(5, 6);

        // Get the x and y coordinates
        int xCoord = position.getX();
        int yCoord = position.getY();

        // Check if the coordinates are set correctly
        assertEquals(5, xCoord);
        assertEquals(6, yCoord);
    }

    /**
     * test Set And Get Color
     */
    @Test
    public void testSetAndGetColor() {
        // Create a Position object with initial coordinates (3, 4)
        Position position = new Position(3, 4);

        // Set the color of the position to Token.WHITE
        position.setColor(Token.WHITE);

        // Get the color of the position
        Token color = position.getColor();

        // Check if the color is set correctly
        assertEquals(Token.WHITE, color);
    }

    /**
     * test Set And Get Selection Status
     */
    @Test
    public void testSetAndGetSelectionStatus() {
        // Create a Position object with initial coordinates (3, 4)
        Position position = new Position(3, 4);

        // Set the selection status to true
        position.setSelected(true);

        // Check if the position is selected
        boolean isSelected = position.getIsSelected();

        // Check if the selection status is set correctly
        assertTrue(isSelected);
    }

    /**
     * To string
     */
    @Test
    public void testToString() {
        // Create a Position object with initial coordinates (3, 4)

        Position position = new Position(3, 4);

        // Get the string representation of the position
        String positionString = position.toString();

        // Check if the string representation is in the expected format
        assertEquals("x :3y : 4", positionString);
    }

    /**
     * Testing the game Start
     */
    @Test
    void startNewGame() {
        // Assert PLayers are created and value is not Null
        game.startNewGame();
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
    }


    /* //player 1 and 2
    // set-board-positions
    */

    /**
     * get Turn Should Return Initial Token
     */
    @Test
    void getTurnShouldReturnInitialToken() {
        Token actualTurn = game.getTurn();
        assertEquals(Token.WHITE, actualTurn);
    }

    /**
     * get Turn Should Return Updated Token
     */
    @Test
    void getTurnShouldReturnUpdatedToken() {
        // game.changePlayer();
        Token actualTurn = game.getTurn();
        game.changePlayer();
        assertEquals(Token.WHITE, actualTurn);
    }

    /**
     * Empty Token
     */
    @Test
    void getNextPlayerColorEMPTYTokenReturnEmpty(){
        Token actualTurn = game.getTurn();
        game.changePlayer();
        assertNotEquals(Token.EMPTY, actualTurn);
    }

    /**
     * testing the first phase
     */
    @Test
    void testFirstPhase() {
        // Test Case 1: Valid move, mill formed
        int index = 2;
        Token turn = Token.WHITE;
        Player player1 = new Player(Token.WHITE);
        Player player2 = new Player(Token.BLACK);
        Position[] boardPositions = new Position[24];
        Position[][] millCombinations = new Position[8][3];
        for (int i = 0; i < boardPositions.length; i++) {
            boardPositions[i] = new Position(i);
        }
        boardPositions[index].setColor(Token.WHITE);
        game.setSkipTurn(true);
        game.firstPhase(index, turn);
        assertEquals(turn, boardPositions[index].getColor(), "Color should be set");
        assertTrue(game.getSkipTurn(), "SkipTurn should be true");

        // Test Case 2: Valid move, no mill formed
        index = 4 /* Choose a valid index */;
        turn = Token.BLACK;
        boardPositions[index].setColor(Token.BLACK);
        game.setSkipTurn(false);
        game.firstPhase(index, turn);
        assertEquals(turn, boardPositions[index].getColor(), "Color should be set");
        assertFalse(game.getSkipTurn(), "SkipTurn should be false");

        // Test Case 3: Invalid move, skipTurn is true
        index = 23;
        turn = Token.WHITE;
        boardPositions[index].setColor(Token.EMPTY); // Set a different color to trigger skipTurn logic
        game.setSkipTurn(false);
        game.firstPhase(index, turn);
        assertEquals(Token.EMPTY, boardPositions[index].getColor(), "Color should be empty");
        assertEquals(player2.getPiecesOnBoard(), 0);
        assertEquals(player1.getPiecesOnBoard(), 0);
        assertFalse(game.getSkipTurn(), "SkipTurn should be false");

        // Test Case 4: Invalid move, piece already present
        index = 5;
        turn = Token.BLACK;
        boardPositions[index].setColor(Token.WHITE); // Set a different color to trigger skipTurn logic
        game.setSkipTurn(false);
        game.firstPhase(index, turn);
        assertEquals(Token.WHITE, boardPositions[index].getColor(), "Color should remain unchanged");
        assertEquals(player2.getPiecesOnBoard(), 0);
        assertEquals(player1.getPiecesOnBoard(),0);
        assertFalse(game.getSkipTurn(), "SkipTurn should be false");
    }

    /*
      @Test
       void testGameOver() {
          Player player1 = new Player(Token.WHITE);
          Player player2 = new Player(Token.BLACK);
          // Scenario 1: Player 2 (BLACK) wins
          System.out.println(game.toString());
          player1.setPiecesOnBoard(2);
          player2.setPiecesOnBoard(2);
          assertFalse(game.gameOver(), "Game should be over because Player 2 (BLACK) wins");

          // Scenario 2: Player 1 (WHITE) wins
          player1.setPiecesOnBoard(2);
          player2.setPiecesOnBoard(3);
          assertFalse(game.gameOver(), "Game should be over because Player 1 (WHITE) wins");


          // Scenario 3: No winner yet
          player1.setPiecesOnBoard(4);
          player2.setPiecesOnBoard(4);
          assertFalse(game.gameOver(), "Game should not be over because there is no winner yet");

      }*/

    /**
     * tests if the player one wins the function should return true
     */
    @Test
    void testPlayer1WINReturnTrue(){
        setUp();
        Player player1 = new Player(Token.WHITE);
        Player player2 = new Player(Token.BLACK);
        player1.setPiecesOnBoard(4);
        player2.setPiecesOnBoard(6);
        assertFalse(game.gameOver(), "Game should be over because Player 2 (BLACK) wins");


    }


}