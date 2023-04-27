package tnt;
import tnt.gui.Gui;

import java.util.Arrays;
import java.util.ArrayList;
import tnt.model.Player;
import tnt.model.Game;

public class Main {
    /**
     * Objekte & Aktionen werden hier testweise erstellt. Muss noch auf Controller verlagert werden! Evtl. sollte Datentyp
     * von Colour & Cards geändert werden?
     */
    public static void main(String[] args) {
        // Kreiiert testweise ein paar Playerobjekte mit zugehörigen Figuren.
        ArrayList<Player> players = new ArrayList<Player>();
        Game game = new Game(players);
        ArrayList<String> levelOfIntelligence = new ArrayList<>((Arrays.asList("Human", "Human", "Human", "easyAI")));
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Mathis", "Basti", "Nils", "Aaron"));
        ArrayList<String> colour = new ArrayList<>(Arrays.asList("Blue", "Red", "Yellow", "Green"));
        int playerAmount = 4;
        int figureAmount = 5;
        game.createPlayer(levelOfIntelligence, playerAmount, names, colour, figureAmount);

        // Printe Ergebnisse
        ArrayList<Player> playerOrder = game.getPlayerOrder();
        System.out.println(playerOrder);
        for (Player player : playerOrder) {
            System.out.println(player.getName());
        }
        // Kreiiert testweise ein Board mit zugehörigen Feldern.
        int x = 6;
        int y = 6;
        game.createBoard(x, y);
    }
}
