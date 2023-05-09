package tnt.gui.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.util.Observer;

public class MainMenuView extends VBox {

    private Button btnContinue = new Button("Go!");
    private Button btnContinue2 = new Button("Go! player");

    public MainMenuView() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        Label lblTitle = new Label("Main Menu!");
        getChildren().addAll(lblTitle, btnContinue, btnContinue2);
    }

    public Button getBtnContinue() {
        return btnContinue;
    }

    public Button getBtnContinue2(){return btnContinue2; }
}
