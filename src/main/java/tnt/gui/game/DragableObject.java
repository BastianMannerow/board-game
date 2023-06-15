package tnt.gui.game;

import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class DragableObject extends Pane {

    public abstract DragableObject copy() throws IOException;
}
