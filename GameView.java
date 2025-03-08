package View;
import Controller.IController;
import Controller.State;
import processing.core.*;
import processing.core.PImage;
import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ControlP5Constants;
import Model.Position;
import Model.Token;
/**
 * The GameView class is responsible for rendering the game interface and handling user interactions.
 * It extends the PApplet class from the Processing library.
 */
public class GameView extends PApplet implements IView {
    /**
     * game width
     */
    int width;
    /**
     * game height
     */
    int height;

    /**
     * This variable represents the button used to exit the game.
     */
    private Button quitButton;
    /**
     * This variable represents the button used to pause the game.
     */
    private Button pauseButton;
    /**
     * This variable represents the button used to resume the game when it is paused.
     */
    private Button resumeButton;
    /**
     * ControlP5 bib message
     */
    private ControlP5 msg;

    /**
     * Game Constructor
     * @param width game width
     * @param height game height
     */
    public GameView(int width,int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Controller Interface
     */
    private IController controller;
    /**
     * Image for the start screen
     */
    private PImage startScreen;
    /**
     * Image for the game background
     */
    private PImage background;
    /**
     * Added flag for background loading
     */
    private boolean backgroundLoaded = false;
    /**
     * gameover image
     */
    private PImage gameOverScreen;
    /**
     * Sets the controller for the view.
     *
     * @param controller The game controller to be set.
     */
    public void setController(IController controller ) {
        this.controller = controller;
    }
    /**
     * Set up the size of the window.
     */
    public void settings() {
        size(width, height);
    }
    /**
     * Initialize the view and load necessary images and buttons.
     */
    public void setup() {
        background = createImage(width, height, ARGB);
        controller.GameStateStart();
        startScreen = loadImage("Order.png");
        gameOverScreen = loadImage("over.png");
        msg = new ControlP5(this);
        int buttonSpacing = 30; // spacing between buttons

        // Create or re-initialize buttons
        //restartButton = msg.addButton("Restart");
        quitButton = msg.addButton("END");
        pauseButton = msg.addButton("Pause");
        resumeButton = msg.addButton("Resume");

        //setupButton(restartButton, "button.png", ControlP5Constants.ACTION_RELEASE, State.GAME_RESET, width - 90, 100);
        setupButton(quitButton, "pause1.png", ControlP5Constants.ACTION_RELEASE, State.GAME_EXIT, width - 90, 100);
        setupButton(pauseButton, "pausebtn.png", ControlP5Constants.ACTION_RELEASE, State.GAME_PAUSE, width - 90, quitButton.getPosition()[1] + quitButton.getHeight() + buttonSpacing);
        setupButton(resumeButton, "play.png", ControlP5Constants.ACTION_RELEASE, State.GAME_RESUME, width - 90, pauseButton.getPosition()[1] + pauseButton.getHeight() + buttonSpacing);
        // Load background image in a separate thread

        new Thread(() -> {
                background = loadImage("ffcc99.png");
                // Notify the main thread that the background image is loaded
                backgroundLoaded = true;
        }).start();


    }
    /**
     * Set up the properties of a button.
     *
     * @param button     The button to be set up.
     * @param imageName  The name of the image for the button.
     * @param action     The action to be performed on button release.
     * @param status     The state to be set on button release.
     * @param x          The x-coordinate of the button.
     * @param y          The y-coordinate of the button.
     */
    private void setupButton(Button button, String imageName, int action,State status,float x,float y) {
        button.setSize(80, 80);
        button.setPosition(x, y);
        button.setImage(loadImage(imageName));
        button.addListenerFor(action, callbackEvent -> controller.setStatus(status));
    }

    public void draw() {
        image(background, 0, 0, width, height);
        controller.nextFrame();
    }

    /**
     * Draw the start screen.
     */
    public void drawStart(){
        drawStart(true);
    }
    public void drawStart(boolean j) {
        // Wait until the background image is loaded
        if (!backgroundLoaded) {
            return;
        }
        if (j){
            background(background);
        }
        else {
            imageMode(CORNER);
            image(gameOverScreen, 0, 0, width, height);
        }

        pauseButton.setVisible(false);
        //restartButton.setVisible(false);
        quitButton.setVisible(false);
        resumeButton.setVisible(false);

        image(startScreen, 0, 0, width, height);

        if (keyPressed) {
            pauseButton.setVisible(true);
            //restartButton.setVisible(true);
            quitButton.setVisible(true);
            resumeButton.setVisible(true);
        }
    }

    /**
     * Check for the spacebar press to start the game.
     */
    public void pressButtonStart()  {
        if(keyPressed){
            if(key == ' ') {
                controller.GameStatePlaying();
            }
        }
    }

    /**
     * Draw the game board with stones.
     *
     * @param boardPositions Array of positions representing the game board.
     */
    public void drawBoard(Position [] boardPositions) {
        rectMode(0);
        createBoardOutline();
        for (int i = 0; i < 24; i++) {
            int x = boardPositions[i].getX();
            int y = boardPositions[i].getY();
            // Draw circular border
            fill(200); // Adjust the color of the border
            stroke(0);
            strokeWeight(6);
            if (boardPositions[i].getColor() == Token.WHITE) {
                fill(255);
                stroke(255);
                ellipse(x, y, 30, 30);
            }
            else if (boardPositions[i].getColor() == Token.BLACK) {
                fill(0);
                stroke(0);
                ellipse(x, y, 30, 30);
            }
            else {
                fill(0);
                ellipse(x, y, 10, 10);
            }
            if (boardPositions[i].getIsSelected() == true){
                hoverStone(boardPositions[i]);
            }
        }
        noStroke();
        noFill();

    }
    /**
     * Draw the outline of the game board.
     */
    public void createBoardOutline(){
        // background(255, 204, 153);
        noFill();
        stroke(0);
        rect(100, 100, 550, 550);
        rect(175, 175, 400, 400);
        rect(250, 250, 250, 250);
        line(100, 375, 250, 375);
        line(500, 375, 650, 375);
        line(375, 100, 375, 250);
        line(375, 650, 375, 500);
    }

    /**
     * Draw the current player's turn indicator.
     *
     * @param turn The current player's turn.
     */
    public void drawPlayer(Token turn) {
        // Draw player indicator rectangle
        fill(255, 204, 153); // Adjust the color of the rectangle
        stroke(0);
        strokeWeight(3);
        rectMode(CENTER);
        rect(375, 710 , 590, 50);


        // Draw ellipse inside the rectangle
        fill((turn == Token.WHITE) ? 255 : 0); // Adjust the color of the ellipse based on the turn
        noStroke();
        ellipse(550, 710, 40, 40);

        // Draw text indicating the current player
        textAlign(LEFT);
        textSize(20); // Adjust the font size
        fill(0); // Adjust text color
        String playerText = "Turn   :   " + ((turn == Token.WHITE) ? "White" : "Black");
        text(playerText, 120, 710);
    }


    /**
     * Draw the current game state indicator.
     *
     * @param currentState The current state of the game.
     */

    public void drawState(State currentState) {
        float indicatorWidth = 150; // Adjust the width of the state indicator
        float indicatorHeight = 40; // Adjust the height of the state indicator
        float x = width / 2.0f;
        float y = 30;

        // Draw background rectangle
        stroke(0);
        strokeWeight(3);
        rectMode(CENTER);
        fill(255, 204, 153);
        rectMode(CENTER);
        rect(x, y, indicatorWidth, indicatorHeight);

        // Draw state text
        textAlign(CENTER, CENTER);
        textSize(18); // Adjust the font size
        fill(0); // Adjust text color

        String stateText = "";

        switch (currentState) {
            case PLAYING_1:
                stateText = "Phase 1";
                fill(255, 0, 0); // Adjust the color for PLAYING_1
                break;
            case PLAYING_2:
                stateText = "Phase 2";
                fill(0, 255, 0); // Adjust the color for PLAYING_2
                break;
            case PLAYING_3:
                stateText = "Phase 3";
                fill(0, 0, 255); // Adjust the color for PLAYING_3
                break;
            // Add more cases for other states if needed

            default:
                break;
        }

        text(stateText, x, y);
    }


    /**
     * Handle mouse click events.
     */
    public void mouseClicked() {
        controller.userInput(dmouseX,dmouseY);
    }
    /**
     * Draw a highlight around a stone on hover.
     *
     * @param boardPosition The position of the stone.
     */
    public void hoverStone(Position boardPosition){
        stroke(0,255,0);
        strokeWeight(2);
        ellipse(boardPosition.getX(), boardPosition.getY(), 35, 35);
        stroke(0);
    }


    /**
     * Display a message on the screen.
     *
     * @param message The message to be displayed.
     *                Should be a String only.
     *
     */
    public void displayMessage(String message) {
        filter( BLUR, 4 );
        fill(24,25,24,128);
        rect(0,150,850,300);
        fill(255,250,250);
        text(message, 230, 300);
    }

    /**
     * Draw the game over screen.
     */
    public void drawGameOver(){
        pauseButton.setVisible(false);
        //restartButton.setVisible(false);
        quitButton.setVisible(false);
        resumeButton.setVisible(false);
        imageMode(CORNER);
        image(gameOverScreen, 0, 0, width, height);
    }
}
