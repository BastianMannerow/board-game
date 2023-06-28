package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import tnt.ResourceHandler;

import java.io.IOException;

/**
 * The view for all building levels
 */
public class BuildingLevel extends DragableObject {

    private int level;

    /**
     * Constructor for the building level
     * @param level the level of the building
     * @throws IOException Exception when the given level does not exist
     */
    public BuildingLevel(int level) throws IOException {
//        FXMLLoader loader;
        ImageView imageView = new ImageView();
        switch (level){
            case -1:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_Kuppel"));
                break;
            case 1:
//                loader = ResourceHandler.getInstance().getFXML("buildingLevel1View");
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_1"));
                break;
            case 2:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_2"));
                break;
            case 3:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_3"));
                break;
            default:
                throw new IOException("Building got wrong level: " + level);
        }
        this.level = level;
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);
        this.getChildren().add(imageView);
    }

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
}
