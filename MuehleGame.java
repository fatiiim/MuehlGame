package Model;
import Controller.State;
/**
 * Represents the game logic for the Nine Men's Morris game.
 * Implements the IGame interface.
 */
public class MuehleGame implements IGame{
    /**
     * Represents the current player's turn
     */
    private Token turn;
    /**
     * Represents the game board positions
     */
    private  Position[] boardPositions;
    /**
     *  Represents possible mill combinations
     */
    private Position[][] millCombinations;
    /**
     * Total positions on the game board, there are 24 positions,
     * Total possible mill combinations, there is 16 mill possible combinations
     * * Number of positions required to form a mill
     *      * a meal can be constructed on 3 stones
     */
    private final int NUM_POSITIONS_OF_BOARD = 24, NUM_MILL_COMBINATIONS = 16,NUM_POSITIONS_IN_EACH_MILL = 3;
    /**
     * Represents Player 1, white, Player 2, Black
     */
    private Player player1, player2;

    /**
     * a setter for turn skipping
     * @param skipTurn swtter of skipturn
     */
    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    /**
     *  Indicates whether the turn should be skipped
     */
    private boolean  skipTurn = false;
    /**
     * Represents the current step in the game
     */
    private Step step = Step.HOVER;
    /**
     Represents the selected stone on the board, -1 means that  no stone selected
     */
    private int stoneSelected = -1;

    /**
     * Initializes the game board and mill combinations, then starts a new game.
     */
    public MuehleGame() {
        initBoards();
        initMillCombinations();
        startNewGame();
        turn = Token.WHITE;
    }
    /**
     * Starts a new game by initializing players and setting the initial turn to white.
     */
    public void startNewGame() {
        player1 = new Player(Token.WHITE);
        player2 = new Player(Token.BLACK);  }

    /**
     * Retrieves Player 2.
     * @return Player 2 instance, Black
     */

    public Player getPlayer2(){
        return player2;
    }

    /**
     * Retrieves Player 1. white
     * @return Player 1 instance
     */
    public Player getPlayer1(){
        return player1;
    }
    public boolean getSkipTurn(){
        return skipTurn;
    }

    /**
     * Initializes the game board positions, setting up their initial coordinates and adjacency.
     * Creates Position objects for each board position and initializes their coordinates.
     * Calls helper methods to set up specific board configurations and adjacency relationships.
     */
    private void initBoards(){
        boardPositions = new Position[NUM_POSITIONS_OF_BOARD];
        for(int i = 0; i < NUM_POSITIONS_OF_BOARD; i++) {
            boardPositions[i] = new Position(i); }
        initBoard(100, 650, 100, 650, 275,0, 0);
        initBoard(175, 650, 175, 650, 200,1, 8);
        initBoard(250, 600, 250, 600, 125,2, 16);
        isAdjacentTo();
    }

    /**
     * Initializes the game board positions based on specific coordinates.
     * @param startX starting X-coordinate for the board
     * @param endX ending X-coordinate for the board
     * @param startY starting Y-coordinate for the board
     * @param endY ending Y-coordinate for the board
     * @param dist distance between the rectangles
     * @param startI initial index for the board positions [i][j] (only three values are used for i)
     * @param k index for board positions (values from 0 to 23)
     */
    private void initBoard(int startX, int endX, int startY, int endY, int dist, int startI, int k ) {
        for (int x = startX ,i=startI,j=0; x <= endX & j < 8; x += dist) {
            for (int y = startY;y <= endY ; y += dist) {
                boardPositions[k].setPosition(x,y);
                k++; j++;
                if (x == 375) { y = y + dist; } }
        }
    }

    /**
     * Specifies adjacent positions for each board position.
     */
    private void isAdjacentTo() {
        int[][] adjacentIndexes = {
                {1, 3}, {1, 2, 9}, {1, 4}, {0, 5, 11},
                {2, 7, 12}, {3, 6}, {5, 7, 14}, {4, 6},
                {9, 11}, {1, 8, 10, 17}, {12, 9}, {3, 8, 13, 19},
                {10, 4, 15, 20}, {11, 14}, {13, 6, 22, 15}, {12, 14},
                {17, 19}, {16, 9, 18}, {17, 20}, {16, 21, 11},
                {18, 23, 12}, {22, 19}, {14, 21, 23}, {20, 22} };
        for (int i = 0; i < NUM_POSITIONS_OF_BOARD; i++) { boardPositions[i].addAdjacentPositionsIndexes(adjacentIndexes[i]);}
    }

    /**
     * Initializes all possible mill combinations.
     */

    private void initMillCombinations() {
        millCombinations = new Position[NUM_MILL_COMBINATIONS][NUM_POSITIONS_IN_EACH_MILL];
        int[][] combinations = {
                {0, 1, 2}, {0, 3, 5}, {0, 4, 7}, {5, 6, 7},
                {8, 11, 13}, {8, 9, 10}, {10, 12, 15}, {13, 14, 15},
                {16, 19, 21}, {16, 17, 18}, {18, 20, 23}, {21, 22, 23},
                {3,11, 19}, {1, 9, 17}, {6, 14, 22}, {4, 12, 20} };
        for (int i = 0; i < NUM_MILL_COMBINATIONS; i++) {
            for (int j = 0; j < NUM_POSITIONS_IN_EACH_MILL; j++) { millCombinations[i][j] = boardPositions[combinations[i][j]]; }
        }
    }

    /**
     * Checks if a mill is formed by the given position in the specified board and mill combinations.
     *
     * @param pos      The position to check for a mill.
     * @param board    The array of board positions.
     * @param millComb The array of mill combinations.
     * @return True if a mill is formed; otherwise, false.
     */
    private boolean checkIfMill(Position pos,Position[] board, Position[][] millComb) {
        for (int i = 0; i < millComb.length; i++) {
            // Check if any of the positions in the mill combination are null
            if (millComb[i][0] == null || millComb[i][1] == null || millComb[i][2] == null) {continue; /*Skip this iteration if any position is null*/}
            // Check if the given position forms a mill with adjacent positions
            if(pos.getPosIdx() ==  millComb[i][0].getPosIdx() ){
                if (board[millComb[i][1].getPosIdx()].getColor() == pos.getColor() && board[millComb[i][2].getPosIdx()].getColor() == pos.getColor() ){
                    return true; }
            }
            if (pos.getPosIdx() ==  millComb[i][1].getPosIdx() ){
                if (board[millComb[i][0].getPosIdx()].getColor() == pos.getColor() && board[millComb[i][2].getPosIdx()].getColor() == pos.getColor() ){
                    return true; }
            }
            if ( pos.getPosIdx() ==  millComb[i][2].getPosIdx())   {
                if (board[millComb[i][1].getPosIdx()].getColor() == pos.getColor() && board[millComb[i][0].getPosIdx()].getColor() == pos.getColor() ){
                    return true;  }
            }
        }
        return false;
    }
    /**
     * Switches the player's turn between white and black.
     */
    public void changePlayer(){
        if (turn == Token.WHITE){ turn = Token.BLACK;  }
        else if (turn == Token.BLACK){ turn = Token.WHITE;   }
    }

    /**
     * Gets the array of board positions.
     *
     * @return The array of board positions.
     */
    public Position[] getBoardPositions() { return boardPositions; }

    /**
     * Gets the current turn (player's color).
     *
     * @return The current turn (player's color).
     */
    public Token getTurn() { return turn; }
    /**
     * Executes the first phase of the game, calculating available pieces, placing them on the board,
     * and checking for mill combinations.
     * The first phase ends when there are no stones for both players left, when they have placed all of them of the Board
     * @param i   The index of the board position where the stone is to be placed.
     * @param turn The player's turn, represented by the Token enum (Token.WHITE or Token.BLACK)
     * Example :  Setting up the game with players and initial board state
     * MuehleGame game = new MuehleGame();
     * Player player1 = new Player(Token.WHITE);
     * Player player2 = new Player(Token.BLACK);
     * game.setPlayers(player1, player2);
     *
     * // Placing stones during the first phase
     * game.firstPhase(0, Token.WHITE); // Player 1 places a stone at position 0
     * game.firstPhase(3, Token.BLACK); // Player 2 places a stone at position 3
     * game.firstPhase(8, Token.WHITE); // Player 1 places a stone at position 8
     *
     * // Skipping turn after forming a mill
     * game.firstPhase(6, Token.BLACK); // Player 2 places a stone at position 6 (forms a mill, skips turn)
     */
    public void firstPhase(int i,Token turn){
        if(boardPositions[i].getColor() == Token.EMPTY && skipTurn == false ) {
            if (turn == Token.WHITE){
                player1.decrementPiecesToSet();
            } else if (turn == Token.BLACK) {
                player2.decrementPiecesToSet();  }
            boardPositions[i].setColor(turn);
            if(checkIfMill(boardPositions[i],boardPositions,millCombinations)){ skipTurn = true; }
            else {
                changePlayer();
                skipTurn = false; }
        }
        else if(skipTurn == true && boardPositions[i].getColor()!= Token.EMPTY && boardPositions[i].getColor() != turn  ) {
            boardPositions[i].setColor(Token.EMPTY);
            if (turn == Token.WHITE){ player2.decrementPiecesOnBoard();}
            else if (turn == Token.BLACK) { player1.decrementPiecesOnBoard();}
            changePlayer();
            skipTurn = false; }
    }

    /**
     * Executes the second phase of the game, involving hover, move, and remove steps based on the game state.
     * Hover : If in the "Hover" step and the selected position is the current player's stone,
     * the function checks adjacent positions.
     * If an adjacent position is empty, the stone becomes selected, and the step transitions to "Move."
     * Move : if a stone is selected and the target position is empty and adjacent to the selected stone, the stone is moved.
     * If a mill is formed, the step transitions to "Remove."
     * Otherwise, the player changes, and the step returns to "Hover."
     * Remove :  if the selected position contains an opponent's stone, it is removed.
     * The opponent's piece count on the board decreases, and the player changes, returning to the "Hover" step.
     * und wider zurück zu hover step
     * @param i board position
     * @param turn wer ist dran
     * Example :  Assuming 'game' is an instance of MuehleGame and 'player1' and 'player2' are initialized players
     * game.secondPhase(3, Token.WHITE);  Player 1 hovers over a stone
     * game.secondPhase(7, Token.WHITE);  Player 1 moves the selected stone
     * game.secondPhase(12, Token.WHITE);  Player 1 removes an opponent's stone
     */
    public void secondPhase(int i,Token turn){
        if (step == Step.HOVER && boardPositions[i].getColor() == turn){
            int [] addj = boardPositions[i].getAdjacentPositionsIndexes();
            if (addj != null){
                for (int j = 0; j < addj.length; j++){
                    if (boardPositions[addj[j]].getColor() == Token.EMPTY){
                        boardPositions[i].setSelected(true);
                        this.step = Step.MOVE;
                        stoneSelected = i; }
                }
            }
        }
        else if (step == Step.MOVE) {
            if(stoneSelected != -1 && boardPositions[i].getColor() == Token.EMPTY && boardPositions[stoneSelected].isAdjacentToThis(i)){
                boardPositions[stoneSelected].setSelected(false);
                boardPositions[stoneSelected].setColor(Token.EMPTY);
                boardPositions[i].setColor(turn);
                stoneSelected = -1;
                if(checkIfMill(boardPositions[i],boardPositions,millCombinations)){step = Step.REMOVE;}
                else {
                    changePlayer();
                    step = Step.HOVER;}
            }
        }
        else if (step == Step.REMOVE) {
            if (boardPositions[i].getColor() != turn) {
                System.out.println("Remove" + turn);
                boardPositions[i].setColor(Token.EMPTY);
                if (turn == Token.WHITE){ player2.decrementPiecesOnBoard(); }
                else if (turn == Token.BLACK) {player1.decrementPiecesOnBoard();}
                changePlayer();
                step = Step.HOVER;
            } }
    }
    /**
     * Executes the third phase of the game, involving hover, move, and remove steps with additional rules.
     * Hover :If in the "Hover" step and the selected position is the current player's stone,
     * the stone becomes selected, and the step transitions to "Move."
     * Move : if a stone is selected and the target position is empty, the stone is moved. If a mill is formed,
     * the step transitions to "Remove." Otherwise, the player changes, and the step returns to "Hover."
     * Remove : if the selected position contains an opponent's stone, it is removed.
     * The opponent's piece count on the board decreases, and the player changes, returning to the "Hover" step.
     * @param i board position
     * @param turn wer ist dran
     * Example : Assuming 'game' is an instance of MuehleGame and 'player1' and 'player2' are initialized players
     * game.thirdPhase(5, Token.BLACK);  Player 2 hovers over a stone
     * game.thirdPhase(10, Token.BLACK);  Player 2 moves the selected stone
     * game.thirdPhase(15, Token.BLACK);  Player 2 removes an opponent's stone
     */

    public void thirdPhase(int i,Token turn){
        if (step == Step.HOVER && boardPositions[i].getColor() == turn){
            boardPositions[i].setSelected(true);
            this.step = Step.MOVE;
            stoneSelected = i;
        }
        else if (step == Step.MOVE) {
            if(stoneSelected != -1 && boardPositions[i].getColor() == Token.EMPTY){
                boardPositions[stoneSelected].setSelected(false);
                boardPositions[stoneSelected].setColor(Token.EMPTY);
                boardPositions[i].setColor(turn);
                stoneSelected = -1;
                if(checkIfMill(boardPositions[i],boardPositions,millCombinations)){ step = Step.REMOVE; }
                else {
                    changePlayer();
                    step = Step.HOVER;
                }
            }
        }
        else if (step == Step.REMOVE) {
            if (boardPositions[i].getColor() != turn){
                boardPositions[i].setColor(Token.EMPTY);
                if (turn == Token.WHITE){ player2.decrementPiecesOnBoard(); }
                else if (turn == Token.BLACK) { player1.decrementPiecesOnBoard(); }
                changePlayer();
                step = Step.HOVER; }
        }
    }

    /**
     * Resets the game to its initial state, clearing the board and resetting player information.
     */
    public void reset() {
        // Reset the current player and start a new game
        startNewGame();
        initBoards();
        // Reset the turn to WHITE
        turn = Token.WHITE;
        // Reset the step to HOVER
        step = Step.HOVER;
        // Reset the stoneSelected to -1
        stoneSelected = -1;
        // Reset the skipTurn flag to false
        skipTurn = false;
        // Clear the color of each board position
        for (int i = 0; i < NUM_POSITIONS_OF_BOARD; i++) {
            boardPositions[i].setColor(Token.EMPTY);
            boardPositions[i].setSelected(false); }
    }

    /**
     * Checks if a player has lost by having fewer than two stones on the board.
     *
     * @return The color of the winning player, or null if no winner yet.
     */
    private Token checkWin() {
        State curentstate = State.PLAYING_1;
        // Check if player 1 has fewer than 3 stones on the board
        if (player1.getPiecesOnBoard() < 3 && (curentstate == State.PLAYING_2 || curentstate == State.PLAYING_3 )) {
            return Token.BLACK; }
        // Check if player 2 has fewer than 3 stones on the board
        if (player2.getPiecesOnBoard() < 3 && (curentstate == State.PLAYING_2 || curentstate == State.PLAYING_3 )) {
            return Token.WHITE; }
        return null; /* No winner yet */}

    /**
     * Checks if the game is over by examining the result of the checkWin() method.
     *
     * @return True if the game is over; otherwise, false.
     */
    public boolean gameOver(){
        if (checkWin() == Token.BLACK || checkWin() == Token.WHITE){
            return true; }
        return false;
    }
    /**
     * Starts a thread to pause the program.
     */
    public void startThread() {
        PauseThread p = new PauseThread();
        p.setPaused(true);
        p.start();
    }
    /**
     * Generates a string representation of the game state, indicating whether the game is lost or ongoing.
     *
     * @return A formatted string indicating the game state.
     */
    public String toString() { return String.format("%s\n",gameOver() ? "you Lost !" : " keep going!!!"); }


}
