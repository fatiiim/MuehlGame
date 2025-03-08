package Model;

/**
 * Position class
 */
public class Position {
    /**
     * Represents the position index.
     */
    private int posIdx;
    /**
     *  Array to store the indices of adjacent positions.
     */
    private int[] adjacentPositionsIndexes;
    /**
     *  Represents the x-coordinate,y-coordinate of the position
     */
    private int x,y;
    /**
     * Represents the player value (1 or 2).
     */
    private Token color; // Added stone variable to store the player value (1 or 2)
    /**
     * Represents the selection status of the position.
     */
    private boolean isSelected = false;
    /**
     *  Initializes a position with a specified index and sets the color as empty (0).
     * @param position at a specific position
     */
    public Position(int position) {
        this.posIdx = position;
        color = Token.EMPTY; /* Initialize stone as empty (0)*/
    }
    public Position(int x, int y) {this.x = x;this.y = y;}
    /**
     * Returns the x-coordinate of the position.
     * @return x
     */
    public int getX() {return x;}

    /**
     * Returns the y-coordinate of the position.
     * @return y
     */
    public int getY() { return y; }

    /**
     * Sets the coordinates of the position.
     * @param x x
     * @param y y
     */
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    /**
     * Returns the index of the position.
     * @return position index
     */
    public int getPosIdx() { return posIdx; }
    /**
     *Adds indices of adjacent positions.
     * @param positions adjacent positions
     */
    public void addAdjacentPositionsIndexes(int... positions) {
        adjacentPositionsIndexes = new int[positions.length];
        System.arraycopy(positions, 0, adjacentPositionsIndexes, 0, positions.length);
    }

    /**
     * Returns an array of indices of adjacent positions.
     * @return adjacent position index
     */
    public int[] getAdjacentPositionsIndexes() {return adjacentPositionsIndexes;}
    /**
     *  Checks if a position with a given index is adjacent to the current position.
     * @param posIndex the psoition index
     * @return if the index is adjacent or not
     */
    public boolean isAdjacentToThis(int posIndex) {
        for (int adjacentPositionsIndex : adjacentPositionsIndexes) {
            if (adjacentPositionsIndex == posIndex) { return true; }
        } return false;
    }

    /**
     *  Returns the color (stone) of the position.
     * @return the player color
     */
    public Token getColor() { return color; }
    /**
     * Returns the selection status of the position.
     * @return if the Stone is selected
     */
    public  boolean getIsSelected(){ return isSelected ; }

    /**
     * Sets the selection status of the position.
     * @param s setter of selected
     */
    public void setSelected(boolean s){ this.isSelected = s; }

    /**
     * Sets the color (stone) of the position.
     * @param color the setted color
     */
    public void setColor(Token color) { this.color = color; }
    /**
     * Provides a string representation of the position in the format "xXyY" where X and Y are the x and y coordinates.
     * @return x and y
     */
    @Override
    public String toString() {return "x :" + x + "y : " + y; }
    /** Example of application :
     *  Creating a Position object with coordinates (3, 4)
     Position position = new Position(3, 4);

     Set the color of the position to Token.WHITE
     position.setColor(Token.WHITE);

     Get the x and y coordinates
     int xCoord = position.getX();
     int yCoord = position.getY();

     Check if the position is selected
     boolean isSelected = position.getIsSelected();

     Set the selection status to true
     position.setSelected(true);

     Get the string representation of the position
     String positionString = position.toString();

     *
     */
}

