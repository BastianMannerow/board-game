package tnt.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * JUnit tests for the ArtificialPlayer class.
 */
public class ArtificialPlayerTest {
    private Game game;
    private Board board;
    private Player player;
    private Field[][] fields;
    private ArtificialPlayer ai;
    private Figure figure;

    @BeforeEach
    public void setup() {
        // Create a game with necessary parameters
        ArrayList<Player> playerOrder = new ArrayList<>();
//        game = new Game(playerOrder, 20, 1, 2, 3, 4, "Test Game", 2, 1, 4);
        game = new Game(2);

        board = new Board(fields, 5, 5);

        // Create a player and add figures
        player = new Player(Player.PlayerType.AI_1, "AI Player", Color.RED, 3, game, "Team A", 10);
        player.addFigure(3); // Add 3 figures for the player

        ai = new ArtificialPlayer();

        // Simulate a previous move where the AI player placed a figure on the board
        board.getField(1, 2).setFigure(figure);
        figure.setX(1);
        figure.setY(2);
    }

    /**
     * Test the easyAI method of the ArtificialPlayer class in MOVE_FIGURE mode.
     * It should execute a random movement for the AI player.
     */
    @Test
    public void testEasyAI_MoveFigureMode() {
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);
        player.addFigure(2);

        ai.easyAI(board, player, game);

        int numFiguresPlaced = 0;
        for (int i = 0; i < board.getXSize(); i++) {
            for (int j = 0; j < board.getYSize(); j++) {
                if (board.getField(i, j).getIsFigureHere()) {
                    numFiguresPlaced++;
                }
            }
        }
        Assertions.assertEquals(1, numFiguresPlaced);
    }

    @Test
    public void testEasyAI_PlaceFiguresMode() {
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);

        ai.easyAI(board, player, game);

        // The AI should have executed a random initial figure placement
        int numFiguresPlaced = 0;
        for (int i = 0; i < board.getXSize(); i++) {
            for (int j = 0; j < board.getYSize(); j++) {
                if (board.getField(i, j).getIsFigureHere()) {
                    numFiguresPlaced++;
                }
            }
        }
        Assertions.assertEquals(1, numFiguresPlaced);
    }

    /**
     * Test the easyAI method of the ArtificialPlayer class in BUILD mode.
     * It should execute a random building for the AI player.
     */
    @Test
    public void testEasyAI_BuildMode() {
        game.setGameStatus(Game.GameStatus.BUILD);

        ai.easyAI(board, player, game);

        int x = figure.getX();
        int y = figure.getY();

        // The AI should have executed a random building
        Assertions.assertTrue(board.getField(1, 1).getTowerLevel() == 1 || board.getField(1, 3).getTowerLevel() == 1
                || board.getField(0, 2).getTowerLevel() == 1 || board.getField(2, 2).getTowerLevel() == 1);
    }

    /**
     * Test the mediumAI method of the ArtificialPlayer class in MOVE mode.
     * It should execute a move with a simple heuristic.
     */
    @Test
    public void testMediumAI_MoveMode() {
        game.setGameStatus(Game.GameStatus.MOVE_FIGURE);

        ai.mediumAI(board, player, game);

    }

    /**
     * Test the hardAI method of the ArtificialPlayer class in MOVE mode.
     * It should execute a move with an advanced reward and punishment heuristic.
     */
    @Test
    public void testHardAI_MoveMode() {
        game.setGameStatus(Game.GameStatus.MOVE_FIGURE);

        ai.hardAI(board, player, game);

    }
}

