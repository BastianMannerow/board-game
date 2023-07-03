package tnt.gui.game;

import tnt.gui.SceneHandler;
import tnt.model.*;

/**
 * The controller for the game
 */
public class GameController{

    private SceneHandler sceneHandler;


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

    public void goToMenu(){
        sceneHandler.loadView("mainMenu");
    }
}
