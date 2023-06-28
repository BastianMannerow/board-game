package tnt.gui.game;

import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * View that can get dragged should extend this class
 */
public abstract class DragableObject extends Pane {

    /**
     * copy the view, so the object can be placed at the cursor as well
     * @return a copy of the object
     * @throws IOException exception if the obejct could not get cloned
     */
    public abstract DragableObject copy() throws IOException;
}
