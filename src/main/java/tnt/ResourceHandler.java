package tnt;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceHandler {
    private static ResourceHandler instance;

    private Map<String, Image> imgs = new HashMap<String, Image>();
    private Map<String, FXMLLoader> fxmls = new HashMap<String, FXMLLoader>();

    private ResourceHandler() {

    }

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
    }

    public Image getImage(String key) {
        if (imgs.get(key) == null) {
            loadImage(key, "jpg", "png", "bmp", "gif");
        }
        return imgs.get(key);
    }

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
