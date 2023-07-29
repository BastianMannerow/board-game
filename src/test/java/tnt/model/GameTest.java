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
//        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 1", Color.RED, 2, null, "1"));
//        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 2", Color.BLUE, 2, null, "2"));
        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 1", Color.BLUE, 2, game, "1 ", 0));
        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 2", Color.BLUE, 2, game, "2 ", 0));

//        game = new Game(playerOrder, 10, 5, 4, 3, 2, "Test Game", 1, 3, 5);
        game = new Game(2);
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
        game.addPlayer(3,"1",0);
        ArrayList<Player> expectedPlayerOrder = new ArrayList<>(playerOrder);
        expectedPlayerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 3", Color.GREEN, 3, game, "1", 0));
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
        expectedPlayerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 2", Color.BLUE, 3, game, "1", 0));
        Assertions.assertEquals(expectedPlayerOrder, game.getPlayerOrder());
    }

    /**
     * Tests the getMaxStepUpHeight method of Game.
     * It should return the maximum height to step up.
     */
    @Test
    public void testGetMaxStepUpHeight() {
        Assertions.assertEquals(1, game.getMaxStepUpHeight());
    }

    /**
     * Tests the getMaxStepDownHeight method of Game.
     * It should return the maximum height to step down.
     */
    @Test
    public void testGetMaxStepDownHeight() {
        Assertions.assertEquals(3, game.getMaxStepDownHeight());
    }

    /**
     * Tests the setMaxStepUpHeight method of Game.
     * It should set the maximum height to step up to the specified value.
     */
    @Test
    public void testSetMaxStepUpHeight() {
        game.setMaxStepUpHeight(2);
        Assertions.assertEquals(2, game.getMaxStepUpHeight());
    }

    /**
     * Tests the setMaxStepDownHeight method of Game.
     * It should set the maximum height to step down to the specified value.
     */
    @Test
    public void testSetMaxStepDownHeight() {
        game.setMaxStepDownHeight(4);
        Assertions.assertEquals(4, game.getMaxStepDownHeight());
    }

    /**
     * Tests the getMaxBuildingLevel method of Game.
     * It should return the maximum building level.
     */
    @Test
    public void testGetMaxBuildingLevel() {
        Assertions.assertEquals(Settings.getVictoryHeight(), game.getVictoryHeight());
    }

    /**
     * Tests the setVictoryHeight method of Game.
     * It should set the victoryHeight to the specified value.
     */
    @Test
    public void testSetVictoryHeight2() {
        int newVictoryHeight = 5;
        game.setVictoryHeight(newVictoryHeight);
        Assertions.assertEquals(newVictoryHeight, game.getVictoryHeight());
    }

    /**
     * Tests the setVictoryHeight method of Player.
     * It should set and return the victory height.
     */
    @Test
    public void testSetVictoryHeight() {
        // Test setting the victory height
        game.setVictoryHeight(3);
        int victoryHeight = game.getVictoryHeight();

        // Verify that the victory height is set correctly
        Assertions.assertEquals(3, victoryHeight);
    }
}
