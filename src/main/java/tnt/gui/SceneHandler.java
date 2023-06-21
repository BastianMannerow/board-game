package tnt.gui;


import javafx.scene.Parent;
import javafx.scene.Scene;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.util.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * Starting point of the JavaFX GUI
 */
public class SceneHandler{

    Scene scene;

    Map<String, Parent> views = new HashMap<>();

    public Scene getScene() {
        return scene;
    }

    public void addMain(Scene scene) {
        this.scene = scene;
    }

    public void add(String viewName, Parent view) {
        views.put(viewName, view);
    }

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
