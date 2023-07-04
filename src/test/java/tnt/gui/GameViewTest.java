package tnt.gui;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tnt.gui.game.FieldView;
import tnt.gui.game.GameView;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Game;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the GameView class.
 */
class GameViewTest {

    private GameView gameView;
    private SceneHandler sceneHandler;
    private Game game;

    /**
     * Set up the necessary dependencies before each test.
     *
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        sceneHandler = new SceneHandler();
        game = new Game();
        gameView = new GameView(sceneHandler, game);
    }

    /**
     * Tests the update method of GameView.
     * It should update the GameView based on the current game state.
     */
    @Test
    void update_ShouldUpdateGameView() {
        // Set up game state for testing
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);
        game.setPlayerOrder(new ArrayList<>());
        game.createBoard(5, 5);
        Figure figure = new Figure();
        game.setLastMovedFigure(figure);

        gameView.update();
    }

    /**
     * Tests the removeHighlights method of GameView.
     * It should remove all highlight images from a FieldView.
     */
    @Test
    void removeHighlights_ShouldRemoveAllHighlightImagesFromFieldView() {
        Field field = new Field(0, 0);
        FieldView fieldView;
        try {
            fieldView = new FieldView(field);
        } catch (IOException e) {
            fail("IOException occurred while creating FieldView");
            return;
        }
        ImageView highlightImage1 = new ImageView();
        ImageView highlightImage2 = new ImageView();
        ArrayList<ImageView> list = new ArrayList<>();
        list.add(highlightImage1);
        list.add(highlightImage2);

        gameView.callRemoveHighlights(list, fieldView);

        assertEquals(0, fieldView.getChildren().size());
    }
}



