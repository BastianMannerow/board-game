package tnt.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.util.Observer;

public class MainMenuView extends VBox {

    private Button btnContinue = new Button("Go!");

    public MainMenuView() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        Label lblTitle = new Label("Some title!");
        getChildren().addAll(lblTitle, btnContinue);
    }

    public Button getBtnContinue() {
        return btnContinue;
    }

}
