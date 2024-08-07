/**
 * The main module of the tnt application.
 */
module tnt {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    exports tnt.gui;
    opens tnt.gui.playerchoosemenu to javafx.fxml;
    opens tnt.gui.mainmenu to javafx.fxml;
    opens tnt.gui.game to javafx.fxml;
    opens tnt.gui.settingsmenu to javafx.fxml;
    opens tnt.gui.saveloadmenu to javafx.fxml;
}
