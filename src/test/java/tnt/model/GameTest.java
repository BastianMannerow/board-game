package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameTest {

    private Game game;
    private ArrayList<Player> playerOrder;

    @BeforeEach
    public void setup() {
        playerOrder = new ArrayList<>();
        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 1", Color.RED, 2, null, "1"));
        playerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 2", Color.BLUE, 2, null, "2"));

        game = new Game(playerOrder, 10, 5, 4, 3, 2, "Test Game");
    }

    @Test
    public void testGetGameName() {
        Assertions.assertEquals("Test Game", game.getGameName());
    }

    @Test
    public void testSetGameName() {
        game.setGameName("New Game");
        Assertions.assertEquals("New Game", game.getGameName());
    }

    @Test
    public void testGetLevelOneTile() {
        Assertions.assertEquals(5, game.getLevelOneTile());
    }

    @Test
    public void testSetLevelOneTile() {
        game.setLevelOneTile(6);
        Assertions.assertEquals(6, game.getLevelOneTile());
    }

    @Test
    public void testGetLevelTwoTile() {
        Assertions.assertEquals(4, game.getLevelTwoTile());
    }

    @Test
    public void testSetLevelTwoTile() {
        game.setLevelTwoTile(3);
        Assertions.assertEquals(3, game.getLevelTwoTile());
    }

    @Test
    public void testGetLevelThreeTile() {
        Assertions.assertEquals(3, game.getLevelThreeTile());
    }

    @Test
    public void testSetLevelThreeTile() {
        game.setLevelThreeTile(4);
        Assertions.assertEquals(4, game.getLevelThreeTile());
    }

    @Test
    public void testGetLevelFourTile() {
        Assertions.assertEquals(2, game.getLevelFourTile());
    }

    @Test
    public void testSetLevelFourTile() {
        game.setLevelFourTile(1);
        Assertions.assertEquals(1, game.getLevelFourTile());
    }

    @Test
    public void testGetPlayerOrder() {
        Assertions.assertEquals(playerOrder, game.getPlayerOrder());
    }

    @Test
    public void testGetAmountOfTurns() {
        Assertions.assertEquals(10, game.getAmountOfTurns());
    }

    @Test
    public void testSetAmountOfTurns() {
        game.setAmountOfTurns(15);
        Assertions.assertEquals(15, game.getAmountOfTurns());
    }

    @Test
    public void testCreateBoard() {
        game.createBoard(3, 3);
        Board board = game.getBoard();
        Assertions.assertNotNull(board);
        Assertions.assertEquals(3, board.getXSize());
        Assertions.assertEquals(3, board.getYSize());
    }

    @Test
    public void testSetMoveMode() {
        game.setMoveMode();
        Assertions.assertEquals(Game.GameStatus.MOVE_FIGURE, game.getGameStatus());
    }

    @Test
    public void testSetBuildMode() {
        game.setBuildMode();
        Assertions.assertEquals(Game.GameStatus.BUILD, game.getGameStatus());
    }

    @Test
    public void testSetGameOverMode() {
        game.setGameOverMode();
        Assertions.assertEquals(Game.GameStatus.GAME_OVER, game.getGameStatus());
    }

    @Test
    public void testAddPlayer() {
        game.addPlayer(3);
        ArrayList<Player> expectedPlayerOrder = new ArrayList<>(playerOrder);
        expectedPlayerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 3", Color.GREEN, 3, null, "1"));
        Assertions.assertEquals(expectedPlayerOrder, game.getPlayerOrder());
    }

    @Test
    public void testRemovePlayer() {
        Player playerToRemove = playerOrder.get(0);
        game.removePlayer(playerToRemove);
        ArrayList<Player> expectedPlayerOrder = new ArrayList<>();
        expectedPlayerOrder.add(new Player(Player.PlayerType.HUMAN, "Player 2", Color.BLUE, 2, null, "1"));
        Assertions.assertEquals(expectedPlayerOrder, game.getPlayerOrder());
    }
}
