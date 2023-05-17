package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainMenuController {

    Parent playerChooseMenu;
    Parent mainMenu;
    Scene mainScene;
    @FXML
    private HBox wholeMenu;
    @FXML
    private Button gotoGame;

    public MainMenuController(Scene scene, Parent playerChooseMenu) {

        this.playerChooseMenu = playerChooseMenu;
        this.mainScene = scene;
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void gotoGame() {
        mainScene.setRoot(playerChooseMenu);
//        System.out.println(playerChooseMenu);
//        System.out.println(mainScene.getRoot());
    }

//    public void setPlayerChoose(Parent choosePlayerMenu) {
////        System.out.println(" A: " + choosePlayerMenu);
////        System.out.println(" B: " + this.playerChooseMenu);
////        this.playerChooseMenu = playerChooseMenu;
//        System.out.println(" C: " + choosePlayerMenu);
//        System.out.println(" D: " + this.playerChooseMenu);
//
//    }
    public Parent getPlayerChoose() {
        return this.playerChooseMenu;

    }

    public void setMainView(MainMenuView mainView) {
        mainMenu = mainView;
        mainScene.setRoot(mainMenu);
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setChoosePlayerMenu(Parent choosePlayerMenu) {
        this.playerChooseMenu = choosePlayerMenu;
    }
}
