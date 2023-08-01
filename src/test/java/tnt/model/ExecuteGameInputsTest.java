package tnt.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * JUnit tests for the ExecuteGameInputs class.
 */
public class ExecuteGameInputsTest {
    private Game game;
    private Player player;
    private Board board;
    private Field field;
    private Figure figure;

    @BeforeEach
    public void setup() {
        ArrayList<Player> playerOrder = new ArrayList<>();
        game = new Game(playerOrder, 12, "Test Game", 1, 3, 3, true);
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, 2, game, "Team A", 10);
        board = new Board(new Field[5][5], 5, 5);
        field = new Field(2, 2);
        figure = new Figure(1, 1, game, player);
        player.addFigure(2);
        game.nextPlayersTurn();
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);
        game.createBoard(5,5);
        figure.setPlaced();
        field.setFigure(figure);
    }

    /**
     * Test case for placeFigure method when not in PLACE_FIGURES mode.
     * It should return false.
     */
    @Test
    public void testPlaceFigure_NotInPlaceFiguresMode_ReturnsFalse() {
        game.setGameStatus(Game.GameStatus.SELECT_PLAYER);
        boolean result = ExecuteGameInputs.placeFigure(figure, field);
        Assertions.assertFalse(result);
    }

    /**
     * Test case for placeFigure method when in MOVE_FIGURE mode.
     * It should return false.
     */
    @Test
    public void testPlaceFigure_CheckMoveFig_ReturnsFalse() {
        game.setGameStatus(Game.GameStatus.MOVE_FIGURE);
        boolean result = ExecuteGameInputs.placeFigure(figure, field);
        Assertions.assertFalse(result);
    }

    /**
     * Test case for placeFigure method when in PLACE_FIGURES mode and the figure is not placed.
     * It should return true.
     */
    @Test
    public void testPlaceFigure_CheckPlaceFig_NotPlaced_ReturnsTrue() {
        figure.setPlaced();
        boolean result = ExecuteGameInputs.placeFigure(figure, field);
        Assertions.assertTrue(result);
    }

    /**
     * Test case for placeFigure method when in PLACE_FIGURES mode and the figure is already placed.
     * It should return false.
     */
    @Test
    public void testPlaceFigure_CheckPlaceFig_AlreadyPlaced_ReturnsFalse() {
        boolean result = ExecuteGameInputs.placeFigure(figure, field);
        Assertions.assertFalse(result);
    }

    /**
     * Test case for buildObject method when not in BUILD mode.
     * It should return false.
     */
    @Test
    public void testBuildObject_NotInBuildMode_ReturnsFalse() {
        game.setGameStatus(Game.GameStatus.SELECT_PLAYER);
        boolean result = ExecuteGameInputs.buildObject(1, field);
        Assertions.assertFalse(result);
    }

    /**
     * Test case for buildObject method with an invalid build level.
     * It should return false.
     */
    @Test
    public void testBuildObject_InvalidBuildLevel_ReturnsFalse() {
        boolean result = ExecuteGameInputs.buildObject(2, field);
        Assertions.assertFalse(result);
    }

    /**
     * Test case for buildObject method with a valid build level when not at max building level.
     * It should return true.
     */
    @Test
    public void testBuildObject_ValidBuildLevel_NotMaxBuildingLevel_ReturnsTrue() {
        boolean result = ExecuteGameInputs.buildObject(1, field);
        Assertions.assertTrue(result);
    }

    /**
     * Test case for buildObject method with a valid build level at max building level.
     * It should return true and set the tower as complete.
     */
    @Test
    public void testBuildObject_ValidBuildLevel_MaxBuildingLevel_ReturnsTrue() {
        field.setTowerLevel(2);
        boolean result = ExecuteGameInputs.buildObject(-1, field);
        Assertions.assertTrue(result);
        Assertions.assertTrue(field.getTowerComplete());
    }
}
