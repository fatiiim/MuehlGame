package Controller;
import Model.IGame;
import Model.Token;
import View.IView;
import static java.lang.System.exit;
/**
 * The GameController class serves as the controller in the MVC architecture for the Muehle game.
 * It handles interactions between the model (game logic) and the view (user interface).
 */
public class GameController implements IController{
    /**
     * Reference to the game model
     */
    private IGame model;
    /**
     * Reference to the game view
     */
    private IView view;
    /**
     * Current state of the game
     */
    private State currentState ;
    /**
     *  Current player's turn
     */
    private Token turn = Token.WHITE;
    /**
     * Flag to track whether the game is in a paused state
     */
    private boolean isPause = false;

    /**
     * Default constructor for GameController.
     */
    private Token lastPlayer = Token.EMPTY;

    /**
     * game controller constructor
     */
    public GameController(){


    }
    /**
     * Sets the model for the controller.
     *
     * @param model The game model to be set.
     */
    public void setModel(IGame model){
        this.model = model;
    }

    /**
     * Sets the view for the controller.
     *
     * @param view The game view to be set.
     */
    public void setView(IView view) {
        this.view = view;
    }
    /**
     * Handles the logic for the next frame of the game based on its current state.
     */
    public void nextFrame() {
        switch (currentState) {
            case START-> {
                view.drawStart();
                view.pressButtonStart();
            }
            case PLAYING_1, PLAYING_2,PLAYING_3-> {
                view.drawBoard(model.getBoardPositions());
                view.drawState(currentState);
                view.drawPlayer(turn);
            }
            case GAME_OVER -> {
                view.drawStart(false);
                view.pressButtonStart();
                model.reset();
                turn = Token.WHITE;
            }

            case GAME_PAUSE -> {
                if (!model.gameOver()) {
                    view.drawBoard(model.getBoardPositions());
                    isPause = true;
                    this.model.startThread();
                    view.displayMessage(("""
                                          PAUSE \s
                                    to continue
                                 click on Play\
                                """));

                }
            }
            case GAME_RESUME -> {
                if (!model.gameOver()) {
                    isPause = false;
                    view.displayMessage(("Game ist schon an"));
                    view.drawBoard(model.getBoardPositions());
                    currentState = State.PLAYING_1;
                }
            }


            case GAME_EXIT -> exit(1);

        }


    }
    /**
     * Sets the game state to START.
     */
    public void GameStateStart(){
        currentState = State.START;
    }
    /**
     * Sets the game state to PLAYING_1 and starts a new game in the model.
     */
    public void GameStatePlaying(){
        currentState = State.PLAYING_1;
        model.startNewGame();
    }

    /**
     * Handles user input based on mouse coordinates.
     *
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     */
    public void userInput(int mouseX, int mouseY){
        for (int i = 0; i < 24; i++) {
            int ellipseX = model.getBoardPositions()[i].getX();
            int ellipseY = model.getBoardPositions()[i].getY();
            if (mouseX >= ellipseX - 40 && mouseX <= ellipseX + 40 && mouseY >= ellipseY - 40 && mouseY <= ellipseY + 40) {
                if(currentState == State.PLAYING_1){
                    model.firstPhase(i,turn);
                }
                else if (currentState == State.PLAYING_2){
                    model.secondPhase(i,turn);
                }
                else if (currentState == State.PLAYING_3){
                    if (lastPlayer == turn || turn == Token.EMPTY || lastPlayer == Token.EMPTY){
                        model.thirdPhase(i,turn);
                    }
                    else {
                        model.secondPhase(i,turn);
                    }

                }
                if ( currentState == State.GAME_OVER || currentState == State.GAME_PAUSE || currentState == State.GAME_RESUME) {
                    return;
                }

                updateTurn();

            }

        }
    }
    /**
     * Updates the game turn based on the model's turn and handles state transitions.
     */
    private void updateTurn(){
        System.out.println("CURRENTSTATE : " + currentState);
        turn = model.getTurn();
        // if player 2 ended with a Mill an error will be occured (state change befor removing the stone )
        if(model.getPlayer2().getPiecesToSet() == 0 && !model.getSkipTurn()){
            currentState = State.PLAYING_2;
        }
        if(currentState == State.PLAYING_2 && (model.getPlayer2().getPiecesOnBoard() == 3 ||  model.getPlayer1().getPiecesOnBoard() == 3 )){
            currentState = State.PLAYING_3;
            if (model.getPlayer1().getPiecesOnBoard() == 3){
                lastPlayer = model.getPlayer1().getColor();
            }
            else if (model.getPlayer2().getPiecesOnBoard() == 3){
                lastPlayer = model.getPlayer2().getColor();
            }
            System.out.println("lastplayer " + lastPlayer);
            if (model.getPlayer2().getPiecesOnBoard() == 3 && model.getPlayer1().getPiecesOnBoard() == 3 ){
                lastPlayer = Token.EMPTY;
                System.out.println("Last player is : " + lastPlayer);
            }

        }
        if (currentState != State.PLAYING_1 && (model.getPlayer2().getPiecesOnBoard() < 3 || model.getPlayer1().getPiecesOnBoard() < 3 ) ){
            currentState = State.GAME_OVER;
            System.out.println("Game Over !!");
        }

        System.out.println(" pieces on board für Player 1 " + model.getPlayer1().getPiecesOnBoard() + " pieces on board für Player 2" + model.getPlayer2().getPiecesOnBoard() );


    }
    /**
     * Sets the game state to the specified state.
     *
     * @param currentState The state to set.
     */
    public void setStatus(State currentState) {
        this.currentState = currentState;
    }


}
