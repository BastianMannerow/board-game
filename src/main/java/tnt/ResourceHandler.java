package tnt;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Hold the resources needed by the game and view
 */
public class ResourceHandler {
    private static ResourceHandler instance;

    private Map<String, Image> imgs = new HashMap<String, Image>();
    private Map<String, FXMLLoader> fxmls = new HashMap<String, FXMLLoader>();
    private Map<String, String> csss = new HashMap<String, String>();

    private ResourceHandler() {

    }

    /**
     * Getter for the only one instance of the resource handler
     * @return the only one resource handler
     */
    public static ResourceHandler getInstance() {
        if (instance == null) {
            instance = new ResourceHandler();
        }
        return instance;
    }

    private void loadImage(String name, String... extensions) {
        URL url = null;
        for (String ext : extensions) {
            url = ResourceHandler.class.getResource(name + "." + ext);
            if (url != null) {
                break;
            }
        }

        Image image = new Image(url.toExternalForm(), true);
        imgs.put(name, image);

    }

    /**
     * getter for the image identified by the name
     * @param key the filename of the picture without extension
     * @return the requested image
     */
    public Image getImage(String key) {
        if (imgs.get(key) == null) {
            loadImage(key, "jpg", "png", "bmp", "gif");
        }
        return imgs.get(key);
    }

    /**
     * getter for a style
     * @param key the filename of the style file
     * @return the style content
     */
    public String getStyle(String key) {
        if (csss.get(key) == null) {
            loadStyle(key);
        }
        return csss.get(key);
    }

    private void loadStyle(String name) {
        String css = ResourceHandler.class.getResource(name + ".css").toExternalForm();
        csss.put(name, css);
    }

    /**
     * getter for a fxml file
     * @param key the filename of the fxml
     * @return the loader of the needed fxml
     */
    public FXMLLoader getFXML(String key) {
        if (fxmls.get(key) == null) {
            loadFXML(key);
        }
        return fxmls.get(key);
    }

    private void loadFXML(String name) {
        FXMLLoader fxml = new FXMLLoader(ResourceHandler.class.getResource(name + ".fxml"));
        fxmls.put(name, fxml);
    }

}
