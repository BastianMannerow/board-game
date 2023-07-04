package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Unit tests for the Game class.
 */
public class GameTest {

    private Game game;
    private ArrayList<Player> playerOrder;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        playerOrder = new ArrayList<>();
        playerOrder.add(new Player("", "Player 1", Color.RED, 2, null, 1));
        playerOrder.add(new Player("", "Player 2", Color.BLUE, 2, null, 1));

        game = new Game(playerOrder, 10, 5, 4, 3, 2, "Test Game");
    }

    /**
     * Tests the getGameName method of Game.
     * It should return the name of the game.
     */
    @Test
    public void testGetGameName() {
        Assertions.assertEquals("Test Game", game.getGameName());
    }

    /**
     * Tests the setGameName method of Game.
     * It should set the name of the game to the specified value.
     */
    @Test
    public void testSetGameName() {
        game.setGameName("New Game");
        Assertions.assertEquals("New Game", game.getGameName());
    }

    /**
     * Tests the createBoard method of Game.
     * It should create a new board with the specified dimensions and assign it to the game.
     */
    @Test
    public void testCreateBoard() {
        game.createBoard(3, 3);
        Board board = game.getBoard();
        Assertions.assertNotNull(board);
        Assertions.assertEquals(3, board.getXSize());
        Assertions.assertEquals(3, board.getYSize());
    }

    /**
     * Tests the setMoveMode method of Game.
     * It should set the game status to MOVE_FIGURE.
     */
    @Test
    public void testSetMoveMode() {
        game.setMoveMode();
        Assertions.assertEquals(Game.GameStatus.MOVE_FIGURE, game.getGameStatus());
    }

    /**
     * Tests the setBuildMode method of Game.
     * It should set the game status to BUILD.
     */
    @Test
    public void testSetBuildMode() {
        game.setBuildMode();
        Assertions.assertEquals(Game.GameStatus.BUILD, game.getGameStatus());
    }

    /**
     * Tests the setGameOverMode method of Game.
     * It should set the game status to GAME_OVER.
     */
    @Test
    public void testSetGameOverMode() {
        game.setGameOverMode();
        Assertions.assertEquals(Game.GameStatus.GAME_OVER, game.getGameStatus());
    }

    /**
     * Tests the addPlayer method of Game.
     * It should add a new player to the game with the specified player number and default properties.
     */
    @Test
    public void testAddPlayer() {
        game.addPlayer(3);
        ArrayList<Player> expectedPlayerOrder = new ArrayList<>(playerOrder);
        expectedPlayerOrder.add(new Player("", "Player 3", Color.GREEN, 3, null, 1));
        Assertions.assertEquals(expectedPlayerOrder, game.getPlayerOrder());
    }

    /**
     * Tests the removePlayer method of Game.
     * It should remove the specified player from the game.
     */
    @Test
    public void testRemovePlayer() {
        Player playerToRemove = playerOrder.get(0);
        game.removePlayer(playerToRemove);
        ArrayList<Player> expectedPlayerOrder = new ArrayList<>();
        expectedPlayerOrder.add(new Player("", "Player 2", Color.BLUE, 2, null, 1));
        Assertions.assertEquals(expectedPlayerOrder, game.getPlayerOrder());
    }
}
