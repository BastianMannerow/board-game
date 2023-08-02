package tnt.gui.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import tnt.ResourceHandler;
import tnt.gui.GUISettings;
import tnt.gui.SizeHandler;
import tnt.util.Observer;

import java.io.IOException;


/**
 * The view for all building levels
 */

public class BuildingLevel extends DragableObject implements Observer {

    private int level;
    private ImageView imageView;
    private static final int MAX_BUILD_IN_LEVEL = 5;

    /**
     * Constructor for the building level
     * @param level the level of the building
     * @throws IOException Exception when the given level does not exist
     */
    public BuildingLevel(int level) throws IOException {
        GUISettings.getInstance().addObserver(this);
        imageView = new ImageView();
        this.level = level;
        imageView.setPreserveRatio(true);
        SizeHandler.getInstance().addObserver(this);
        imageView.setFitHeight(SizeHandler.getPrefSize());
        this.getChildren().add(imageView);
        if(level > MAX_BUILD_IN_LEVEL){
            Label height = new Label(String.valueOf(level));
            height.setTextFill(Color.WHITE);
            height.setAlignment(Pos.CENTER);
            this.getChildren().add(height);
        }
        update();
    }

    /**
     * Copys the Buildinglevel
     * @return Buildinglevel
     * @throws IOException
     */
    @Override
    public DragableObject copy() throws IOException {
        return new BuildingLevel(level);
    }

    /**
     * getter for the level
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     *  The update method for each Building if it is changed in the model
     */
    @Override
    public void update() {
        String prefix = GUISettings.getTheme();
        switch (level){
            case 0:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_Kuppel"));
                break;
            case 1:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_1"));
                break;
            case 2:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_2"));
                break;
            case 3:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_3"));
                break;
            case 4:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_4"));
                break;
            default:
                imageView.setImage(ResourceHandler.getInstance().getImage(prefix + "Turm_5"));
        }
        ((ImageView) this.getChildren().get(0)).setFitHeight(SizeHandler.getPrefSize());
        if(level > MAX_BUILD_IN_LEVEL){
            Label height = (Label) this.getChildren().get(1);
            height.setPrefSize(SizeHandler.getPrefSize(),SizeHandler.getPrefSize());
            height.setStyle("-fx-font: " + SizeHandler.getPrefSize()/2 + " arial; -fx-font-weight: bold");
        }
    }
}
