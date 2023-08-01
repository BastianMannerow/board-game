package tnt.gui.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tnt.gui.SceneHandler;
import tnt.model.*;

/**
 * The controller for the game
 */
public class GameController{

    private SceneHandler sceneHandler;

    @FXML
    Button mainMenu;
    @FXML
    Button playerMenuButton;


    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    /**
     * places a figure on the field if possible
     * @param figure the figure to be placed
     * @param field the field the figure should be placed to
     * @return if the placing was successful
     */
    public boolean placeFigure(Figure figure, Field field) {
        return ExecuteGameInputs.placeFigure(figure, field);
    }

    /**
     * build on the field if possible
     * @param buildLevel the level of the level to be build
     * @param field the field where the building should be placed
     * @return if the build was successful
     */
    public boolean buildObject(int buildLevel, Field field) {
        return ExecuteGameInputs.buildObject(buildLevel, field);
    }

    /**
     * the function swaps the scene to the mainMenu if it saved in the SceneHandler
     */
    @FXML
    void goToMenu(){
        sceneHandler.loadView("mainMenu");
    }
    /**
     * the function swaps the scene to the playerMenu if it saved in the SceneHandler
     */
    @FXML @SuppressWarnings("UnusedPrivateMethod")
    private void playerMenu(){
        sceneHandler.loadView("playerMenu");
    }

    /**
     * If this function is called and the sceneHandler has saved a viewName "End" then switch to EndView
     */
    public void goToEnd() {
        sceneHandler.loadView("End");
    }
}
