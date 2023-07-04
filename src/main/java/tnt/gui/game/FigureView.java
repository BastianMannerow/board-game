package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import tnt.ResourceHandler;
import tnt.gui.StaticSizeHandler;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.util.Observer;

import java.io.IOException;

/**
 * The view for the figure
 */
public class FigureView extends DragableObject implements Observer{

    private Player player;

    private Figure figure;


    /**
     * Contructor for the view
     * @param player the player belonging to the figure
     * @param figure the figure belonging to the view
     * @throws IOException Exception when the fxml file has an error / does not exist
     */

    public FigureView(Player player, Figure figure) throws IOException {
        this.player = player;
        this.figure = figure;
        FXMLLoader loader = ResourceHandler.getInstance().getFXML("figureView");
        loader.setRoot(this);
        loader.load();
        ((Circle)((StackPane) this.getChildren().get(0)).getChildren().get(0)).setFill(player.getColor());
        String name = player.getName();
        ((Label)((StackPane) this.getChildren().get(0)).getChildren().get(1)).setText(name.substring(0,Math.min(5, name.length())));
        figure.addObserver(this);
        player.addObserver(this);
        StaticSizeHandler.getInstance().addObserver(this);
        update();
    }

    /**
     * Getter for the player
     * @return the player belonging to the figure
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for the figure
     * @return the figure belonging to the view
     */

    public Figure getFigure() {
        return figure;
    }

    @Override
    public void update() {
        ((Circle)((StackPane) this.getChildren().get(0)).getChildren().get(0)).setFill(player.getColor());
        String name = player.getName();
        ((Label)((StackPane) this.getChildren().get(0)).getChildren().get(1)).setText(name.substring(0,Math.min(5, name.length())));
        this.setLayoutX(0);
        ((Circle)((StackPane) this.getChildren().get(0)).getChildren().get(0)).setRadius(StaticSizeHandler.getPrefSize()/4);
        ((StackPane) this.getChildren().get(0)).setPrefHeight(StaticSizeHandler.getPrefSize());
        ((StackPane) this.getChildren().get(0)).setPrefWidth(StaticSizeHandler.getPrefSize());
    }

    @Override
    public FigureView copy() throws IOException
    {
        return new FigureView(this.player, this.figure);
    }
}
