package Model;

/**
 * Player class
 */
public class Player{
    /**
     * Represents the color assigned to the player.
     */
    private Token color;
    /**
     * Represents the number of pieces the player has yet to set on the board.
     */
    private int piecesToSet = 9,piecesOnBoard = 0;
    /**
     *  Represents the number of pieces currently on the board.
     */

    /**
     *  Initializes a player with a specified color.
     * @param color
     */
    public Player(Token color) {
        this.color = color;
    }

    /**
     * Decrements the count of pieces the player has yet to set and increments the count of pieces on the board.
     */

    public void decrementPiecesToSet() { piecesToSet--; piecesOnBoard++;}
    /**
     * Decrements the count of pieces currently on the board.
     */
    public void decrementPiecesOnBoard() { if (piecesOnBoard > 0) { piecesOnBoard--;}}

    /**
     * getters and setters
     */

    /**
     * Returns the color assigned to the player.
     * @return returns black or white
     */
    public Token getColor() {
        return color;
    }
    /**
     * Returns the number of pieces currently on the board.
     * @return the number of pieces on board
     */
    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }
    /**
     *  Sets the count of pieces currently on the board.
     * @param piecesOnBoard
     */
    public void setPiecesOnBoard(int piecesOnBoard) {
        this.piecesOnBoard = piecesOnBoard;
    }
    /**
     *  Returns the number of pieces the player has yet to set.
     * @return numbers of pieces left to set
     */
    public int getPiecesToSet() { return piecesToSet;}

    /**
     *  Sets the color assigned to the player.
     * @param color black or white
     */
    public void setColor(Token color) {
        this.color = color;
    }

/** Application example:
 * public class Game {
 *     public static void main(String[] args) {
 *         // Creating two players
 *         Player player1 = new Player(Token.White);
 *         Player player2 = new Player(Token.Black);
 *
 *         // Variable to keep track of the current player
 *         Player currentPlayer = player1;
 *
 *         // Simulate the game loop
 *         for (int turn = 1; turn <= 18; turn++) {
 *             System.out.println("Turn " + turn + ": " + currentPlayer.getColor() + " player's turn");
 *
 *             // Player places a piece on the board
 *             currentPlayer.decrementPiecesToSet();
 *             currentPlayer.decrementPiecesOnBoard();
 *             System.out.println("Pieces on board: " + currentPlayer.getPiecesOnBoard());
 *             System.out.println("Pieces left to set: " + currentPlayer.getPiecesToSet());
 *
 *             // Switch to the next player
 *             currentPlayer = (currentPlayer == player1) ? player2 : player1;
 *
 *             System.out.println("----------------------------------------");
 *         }
 *
 *         // Print final results
 *         System.out.println("Game Over!");
 *         System.out.println("Player 1 pieces on board: " + player1.getPiecesOnBoard());
 *         System.out.println("Player 2 pieces on board: " + player2.getPiecesOnBoard());
 *     }
 * }
 */

}


