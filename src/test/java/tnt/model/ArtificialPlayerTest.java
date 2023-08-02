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
        ArrayList<Player> playerOrder = new ArrayList<>();
        game = new Game(playerOrder, 12, "Test GameX", 1, 3, 3, true);
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);
        Settings.setActualGame(game);

        Player player1 = new Player(Player.PlayerType.HUMAN, "Player1", Color.BLUE, 2, game, "1", 0);
        player1.initPlayer();
        playerOrder.add(player1);
        Player player2 = new Player(Player.PlayerType.HUMAN, "Player2", Color.BLUE, 2, game, "2", 0);
        player2.initPlayer();
        playerOrder.add(player2);

        game.setNumberOfTile((new int[] {10,10,10,10,10,10}));
        player1.setNumberOfTile(new int[] {10,10,10,10,10,10});
        player2.setNumberOfTile(new int[] {10,10,10,10,10,10});

        player=game.getPlayerOrder().get(0);
        game.createBoard(5,5);
        game.setVictoryHeight(3);
        game.getBoard().getField(1,1).setTowerLevel(3);
        board=game.getBoard();









    }

    /**
     * Test the easyAI method of the ArtificialPlayer class in MOVE_FIGURE mode.
     * It should execute a random movement for the AI player.
     */
    @Test
    public void testEasyAI_MoveFigureMode() {
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);

        ai.easyAI(board, player, game);
        ai.easyAI(board, player, game);

        int numFiguresPlaced = 0;
        for (int i = 0; i < board.getXSize(); i++) {
            for (int j = 0; j < board.getYSize(); j++) {
                if (board.getField(i, j).getIsFigureHere()) {
                    numFiguresPlaced++;
                }
            }
        }
        Assertions.assertEquals(2, numFiguresPlaced);
        Field freefield = null;
        for (int i = 1;i<board.getXSize();i++) {
            for (int j = 1; j < board.getYSize(); j++) {
                if(!board.getField(i,j).getIsFigureHere()){
                    freefield=board.getField(i,j);
                }
            }
        }
        ExecuteGameInputs.placeFigure( game.getPlayersTurn().getFigure().get(0), freefield);
        Field freefield2 = null;
        for (int i = 1;i<board.getXSize();i++) {
            for (int j = 1; j < board.getYSize(); j++) {
                if(!board.getField(i,j).getIsFigureHere()){
                    freefield2=board.getField(i,j);
                }
            }
        }

        ExecuteGameInputs.placeFigure( game.getPlayersTurn().getFigure().get(1), freefield2);
        Assertions.assertEquals(game.getPlayerOrder().get(1).allFiguresPlaced(),true);
        Figure fig1=player.getFigure().get(0);
        Figure fig2=player.getFigure().get(1);
        ai.easyAI(board, player, game);

    }

    @Test
    public void testEasyAI_PlaceFiguresMode() {
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);

        ai.easyAI(board, player, game);
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
        Assertions.assertEquals(2, numFiguresPlaced);
    }

    /**
     * Test the easyAI method of the ArtificialPlayer class in BUILD mode.
     * It should execute a random building for the AI player.
     */
    @Test
    public void testEasyAI_BuildMode() {
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);

        ai.easyAI(board, player, game);
        ai.easyAI(board, player, game);

        Field freefield = null;
        for (int i = 1;i<board.getXSize();i++) {
            for (int j = 1; j < board.getYSize(); j++) {
                if(!board.getField(i,j).getIsFigureHere()){
                    freefield=board.getField(i,j);
                }
            }
        }
        ExecuteGameInputs.placeFigure( game.getPlayersTurn().getFigure().get(0), freefield);
        Field freefield2 = null;
        for (int i = 1;i<board.getXSize();i++) {
            for (int j = 1; j < board.getYSize(); j++) {
                if(!board.getField(i,j).getIsFigureHere()){
                    freefield2=board.getField(i,j);
                }
            }
        }
        ExecuteGameInputs.placeFigure( game.getPlayersTurn().getFigure().get(1), freefield2);

        ai.easyAI(board, player, game);

        game.setGameStatus(Game.GameStatus.BUILD);

        ai.easyAI(board, player, game);

        boolean build=false;
        int x = game.getLastMovedFigure().getX();
        int y = game.getLastMovedFigure().getY();
        for (int i = 1;i< board.getXSize();i++){
            for (int j = 1;j< board.getYSize();j++){
                if (board.getField(i,j).getTowerLevel()==1){
                    build=true;
                }
            }
        }

        // The AI should have executed a random building
        Assertions.assertTrue(build);
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

