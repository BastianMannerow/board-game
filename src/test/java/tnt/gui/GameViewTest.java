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


class GameViewTest {

    private GameView gameView;
    private SceneHandler sceneHandler;
    private Game game;

    @BeforeEach
    void setUp() throws IOException {
        sceneHandler = new SceneHandler();
        game = new Game();
        gameView = new GameView(sceneHandler, game);
    }

    @Test
    void update_ShouldUpdateGameView() {
        // Set up game state for testing
        game.setGameStatus(Game.GameStatus.PLACE_FIGURES);
        game.setPlayerOrder(new ArrayList<>());
        game.createBoard(5, 5);
        Figure figure = new Figure();
        game.setLastMovedFigure(figure);

        // Perform actions that trigger the update method
        gameView.update();

        // Assert the expected changes in the game view
        // (e.g., visibility of figureView, clearing of children in the right VBox, etc.)
        // ...
    }

    @Test
    void removeHighlights_ShouldRemoveAllHighlightImagesFromFieldView() {
        // Set up the necessary dependencies
        Field field = new Field(0, 0);  // Create a Field instance with appropriate parameters
        FieldView fieldView;
        try {
            fieldView = new FieldView(field);
        } catch (IOException e) {
            // Handle the IOException (e.g., log the error, fail the test, etc.)
            fail("IOException occurred while creating FieldView");
            return;
        }
        ImageView highlightImage1 = new ImageView();
        ImageView highlightImage2 = new ImageView();
        ArrayList<ImageView> list = new ArrayList<>();
        list.add(highlightImage1);
        list.add(highlightImage2);

        // Call the removeHighlights method
        gameView.callRemoveHighlights(list, fieldView);

        // Assert that all highlight images are removed from the FieldView
        assertEquals(0, fieldView.getChildren().size());
    }
}


