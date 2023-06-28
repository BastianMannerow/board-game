package tnt.gui;


import javafx.scene.Parent;
import javafx.scene.Scene;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.util.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * A scenehandler known by the view, so they can switch between them without knowing all the other view directly
 */
public class SceneHandler{

    Scene scene;

    Map<String, Parent> views = new HashMap<>();

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
        if (views.containsKey(viewName)){
            Parent view = views.get(viewName);
            scene.setRoot(view);
            if (view instanceof Observer) {
                ((Observer) views.get(viewName)).update();
            }
        }
    }
}
