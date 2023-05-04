package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import tnt.model.enums.Gods;
import tnt.model.gods.movement.*;
import tnt.model.gods.sabotage.Persephone;

/**
 * The Game class, which is responsible for general mechanics during the Game.
 */
public class Game {
    private ArrayList<Player> playerOrder;

    /**
     * Constructing an object Game.
     * @param playerOrder
     */
    public Game(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * @return playerOrder
     */
    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * @param playerOrder replaces old playerOrder
     */

    public void setPlayerOrder(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * Creating new player objects and adding them to playerOrder
     *
     * @param playerAmount the amount of new players
     * @param names the names of the new players
     * @param colour the colours of the new players
     * @param figureAmount the amount of figures on the players disposal
     */
    public void createPlayer(ArrayList<String> levelOfIntelligence, int playerAmount, ArrayList<String> names, ArrayList<String> colour, int figureAmount, ArrayList<Gods> gods) {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(levelOfIntelligence.get(i), names.get(i), colour.get(i), new ArrayList<Figure>(), gods.get(i));
            newPlayer.addFigure(figureAmount, gods.get(i));
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }

    /**
     * Creates Board and containing Field objects.
     *
     * @param boardX the Width of the board
     * @param boardY the Height of the board
     */
    public void createBoard(int boardX, int boardY) {
        Field[][] fields = new Field[boardX][boardY];
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                Field field = new Field(i, j);
                fields[i][j] = field;
            }
        }
        Board board = new Board(fields, boardX, boardY);

        // Testprint, kann später entfernt werden.
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                System.out.println(board.getField(i, j));
            }
        }
    }

    /**
     * Checks if the Game is over
     */
    public boolean checkRegularEnding(){
        // Differenzierung 2 oder 4 Spieler einbauen.
        boolean gameEnded = false;
        return gameEnded;
    }

    /**
     * Runs the Game
     */
    public void runGame() {
        boolean gameEnded = false;
        while(gameEnded == false){
            Player activePlayer = playerOrder.get(0);

            // Checks which gods are in the game
            ArrayList<Gods> activeGods = activePlayer.getGods();
            ArrayList <Gods> passiveGods = new ArrayList<Gods>();
            for(Player player:playerOrder){
                passiveGods.addAll(player.getGods());
            }
            for (int i = 1; i < playerOrder.size(); i++) {
                Player player = playerOrder[i];
                passiveGods.addAll(player.getGods());
            }
            // Movement
            ArrayList<Field> validMovement = activePlayer.getValidMovement();
            for(Gods god:passiveGods){
                switch(god){
                    case Aphrodite:
                        validMovement = Aphrodite.sabotage(validMovement);
                    case Athena:
                        validMovement = Athena.sabotage(validMovement);
                    case Hypnus:
                        validMovement = Hypnus.sabotage(validMovement);
                    case Persephone:
                        validMovement = Persephone.sabotage(validMovement);
                }
            }
            activePlayer.executeMove();

            // Building
            ArrayList<Field> validBuilds = activePlayer.getValidBuilds();
            for(Gods god:passiveGods){
                switch(god){
                    case Limus:
                        validBuilds = Limus.sabotage(validBuilds);

            activePlayer.executeBuilds();

            // Checks if the game is over
            if(checkRegularEnding()){
                gameEnded = true;
                break;
            }

            ArrayList<Gods> allGods = new ArrayList<>(activeGods);
            allGods.addAll(passiveGods);
            for(Gods god:allGods){
                switch (god) {
                    case Chronus:
                        if(Chronus.checkSpecialEnding();){
                            gameEnded = true;
                            break;
                    case Eros:
                        if(Eros.checkSpecialEnding();){
                        gameEnded = true;
                        break;
                    case Hera:
                        if(Hera.checkSpecialEnding();){
                        gameEnded = true;
                        break;
                    case Pan:
                        if(Pan.checkSpecialEnding();){
                        gameEnded = true;
                        break;
                    default:
                        continue;
                }
            }

            // Spielerwechsel
            Collections.rotate(playerOrder, -1);
        }
    }
}