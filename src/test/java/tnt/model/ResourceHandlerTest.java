package tnt.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tnt.ResourceHandler;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class ResourceHandlerTest {

    private ResourceHandler handler;

    @BeforeEach
    public void setup() {
        this.handler = ResourceHandler.getInstance();
    }

    @Test
    public void getInstance_NotNull() {
        assertNotNull(this.handler);
    }

    @Test
    public void getInstance_Singleton() {
        ResourceHandler anotherInstance = ResourceHandler.getInstance();
        assertSame(this.handler, anotherInstance);
    }

    @Test
    public void getFXML_FXMLExists() {
        FXMLLoader loader = this.handler.getFXML("End");
        assertNotNull(loader);
    }

}