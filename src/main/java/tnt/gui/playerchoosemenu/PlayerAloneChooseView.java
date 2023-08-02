package tnt.gui.playerchoosemenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import tnt.ResourceHandler;
import tnt.gui.Language;
import tnt.model.Player;
import tnt.util.Observer;

import java.io.IOException;

/**
 * The view for each player in the playerchoose menu
 */
public class PlayerAloneChooseView extends HBox implements Observer {

    private int playerNumber = 0;
    private Player player;

    private ObservableList<Object> playerTypeList;


    /**
     * The constructor for the view
     * @param player the player belonging to this view
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public PlayerAloneChooseView(Player player) throws IOException {
        this.player = player;
        FXMLLoader playerLayout = ResourceHandler.getInstance().getFXML("choosePlayer");
        playerLayout.setRoot(this);
        playerLayout.load();
        player.addObserver(this);
        playerTypeList = FXCollections.observableArrayList(Player.PlayerType.values());
        ChoiceBox playerType = (ChoiceBox) ((VBox) this.getChildren().get(6)).getChildren().get(1);
        playerType.setItems(playerTypeList);
        StringConverter<Player.PlayerType> stringConverter = new StringConverter<Player.PlayerType>() {
            @Override
            public String toString(Player.PlayerType playerType) {
                if (playerType == null){
                    return "";
                }
                return Language.playerType(playerType);
            }

            @Override
            public Player.PlayerType fromString(String s) {
                return null;
            }
        };
        playerType.setConverter(stringConverter);
        Language.getInstance().addObserver(this);
    }

    /**
     * The update Method for continues expansion of the amount of players added to the game
     */
    @Override
    public void update() {


        ((Label) ((VBox) this.getChildren().get(2)).getChildren().get(0)).setText(Language.getTranslation("nameLabel"));
        ((Label) ((VBox) this.getChildren().get(3)).getChildren().get(0)).setText(Language.getTranslation("amountFiguresLabel"));
        ((Label) ((VBox) this.getChildren().get(4)).getChildren().get(0)).setText(Language.getTranslation("colorLabel"));
        ((Label) ((VBox) this.getChildren().get(5)).getChildren().get(0)).setText(Language.getTranslation("teamLabel"));
        ((Label) ((VBox) this.getChildren().get(6)).getChildren().get(0)).setText(Language.getTranslation("playerTypeLabel"));

        ((Label) this.getChildren().get(1)).setText(Language.getTranslation("player") + " " + playerNumber);
        TextField name = (TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1);
        if (name.getText().equals("")){
            name.setPromptText(Language.getTranslation("player") + " " + player.getName());
        } else {
            name.setText(player.getName());
        }
        ColorPicker cp = (ColorPicker) ((VBox) this.getChildren().get(4)).getChildren().get(1);
        ((VBox) cp.getParent()).getChildren().remove(cp);
        ((VBox) this.getChildren().get(4)).getChildren().add(new ColorPicker(player.getColor()));


        TextField team = (TextField) ((VBox) this.getChildren().get(5)).getChildren().get(1);
        if (team.getText().equals("")){
            team.setPromptText(player.getTeam());
        } else {
            team.setText(player.getTeam());
        }
        TextField amountOfFigures = (TextField) ((VBox) this.getChildren().get(3)).getChildren().get(1);
        if (amountOfFigures.getText().equals("")){
            amountOfFigures.setPromptText(Integer.toString(player.getAmountOfFigures()));
        } else {
            amountOfFigures.setText(Integer.toString(player.getAmountOfFigures()));
        }
        ChoiceBox playerType = (ChoiceBox) ((VBox) this.getChildren().get(6)).getChildren().get(1);
        playerType.setItems(FXCollections.observableArrayList()); // set emtpy list());
        playerType.setItems(playerTypeList);
        playerType.setValue(player.getLevelOfIntelligence());
    }

    /**
     * Sets the player indentifier number
     * @param i the unique identifer for the player
     */
    public void setPlayerNumber(int i) {
        this.playerNumber = i;
        update();
    }

    /**
     * Getter for the player
     * @return the player belonging to this view
     */
    public Player getPlayer() {
        return this.player;
    }
}
