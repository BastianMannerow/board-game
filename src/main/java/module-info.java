/**
 * The main module of the tnt application.
 */
module tnt {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    exports tnt.gui;
}
