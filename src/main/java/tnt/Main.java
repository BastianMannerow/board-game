package tnt;
import tnt.gui.Gui;

import java.util.Arrays;
import java.util.ArrayList;
import tnt.model.Player;
import tnt.model.Game;

public class Main {
    public static void main(String[] args) {
        // Kreiert testweise ein paar Playerobjekte mit zugehörigen Figuren.
        ArrayList<Player> players = new ArrayList<Player>();
        Game game = new Game(players);
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Mathis", "Basti", "Nils", "Aaron"));
        ArrayList<String> colour = new ArrayList<>(Arrays.asList("Blue", "Red", "Yellow", "Green"));
        int playerAmount = 4;
        int figureAmount = 5;
        game.createPlayer(playerAmount, names, colour, figureAmount);

        // Printe Ergebnisse
        ArrayList<Player> order = game.getPlayerOrder();
        System.out.println(order);
        for (Player player : order) {
            System.out.println(player.getName());
        }
        // Kreiert testweise ein Board mit zugehörigen Feldern.
        int boardSize = 6;
        game.createBoard(boardSize);
    }
}
