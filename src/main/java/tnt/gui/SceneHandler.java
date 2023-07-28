package tnt.gui;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tnt.ResourceHandler;
import tnt.gui.game.GameView;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.model.Settings;
import tnt.util.Observer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A scenehandler known by the view, so they can switch between them without knowing all the other view directly
 */
public class SceneHandler{

    Stage stage;

    Scene scene;

    Map<String, Parent> views = new HashMap<>();

    /**
     * Constructor of the scenehandler, holding all the scenes
     * @param primaryStage the stage where the scenes are presented
     */
    public SceneHandler(Stage primaryStage) {
        this.stage = primaryStage;
    }


    /**
     * Getter for the whole scene
     * @return the whole scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Setter for the whole scene
     * @param scene the root scene for all views
     */
    public void addMain(Scene scene) {
        this.scene = scene;
    }

    /**
     * adding a view to the scene builder
     * @param viewName the name of the view
     * @param view the to be added
     */
    public void add(String viewName, Parent view) {
        views.put(viewName, view);
    }

    /**
     * Load the view with the name
     * @param viewName the name of the view to be loaded
     */
    public void loadView(String viewName){
        if (!views.containsKey(viewName)){
            createView(viewName);
        }
        if (views.containsKey(viewName)){
            Parent view = views.get(viewName);
            scene.setRoot(view);
            if (view instanceof Observer) {
                ((Observer) views.get(viewName)).update();
            }
        }
    }

    private void createView(String viewName) {
        if (viewName.equals("gameView")){
            // generating the gameview
            try {
                new GameView(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // generate the playerchoose menu
        if (viewName.equals("playerMenu")) {
            try {
                new PlayerChooseView(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * getter for the stage
     * @return the stage
     */
    public Stage getStage(){
        return stage;
    }

//    /**
//     * loads a specific style to the actual scene
//     * @param key the name of the style to be set
//     */
//    public void loadStyle(String key){
//        scene.getStylesheets().clear();
//        scene.getStylesheets().add(ResourceHandler.getInstance().getStyle(key));
//    }
}
