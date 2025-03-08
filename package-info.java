/**
 * The Model package encapsulates the core logic and entities of the Nine Men's Morris game. It consists of the following key components:
 *
 * IGame:
 * Interface defining the contract for the game logic. It provides a blueprint for implementing the essential functionalities of the game.
 *
 * MuehlGame:
 * Class implementing the IGame interface, representing the Nine Men's Morris game. It manages the game state, player information, the game board, mill combinations, and the flow of the game.
 *
 * Player:
 * Entity class representing a player in the game. It holds information about the player's color, the number of stones on the board, and the remaining stones to be placed.
 *
 * Position:
 * Entity class representing a position on the game board. It includes details such as the position index, color, adjacency information, and whether it is selected.
 *
 * PauseThread:
 * Class responsible for managing the pause functionality in the game using threads. It allows for the pausing and resuming of the game.
 *
 * Step:
 * Enum defining the different steps or phases of the game, such as HOVER, MOVE, and REMOVE. It aids in controlling the flow of the game.
 *
 * Token:
 * Enum representing the possible colors of game pieces, including WHITE, BLACK, and EMPTY. It is used to identify players and positions on the game board.
 *
 * This Model package serves as the backbone of the Nine Men's Morris game, encapsulating the entities and logic necessary for its functionality. It ensures a clear separation of concerns and facilitates a modular and maintainable codebase.
 */
package Model;