package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import tnt.model.interfaces.Gods;
import tnt.model.gods.movement.*;
import tnt.model.gods.victory.*;
import tnt.model.gods.sabotage.*;

/**
 * The Game class, which is responsible for general mechanics during the Game.
 */
public class Game {
    private ArrayList<Player> playerOrder;
    private Board board;
    private int amountOfTurns;
    private int levelOneTile;
    private int levelTwoTile;
    private int levelThreeTile;


    /**
     * Constructing an object Game.
     * @param playerOrder
     * @param amountOfTurns How many turns are completed so far (at beginning 0). It's for the highscore
     * @param levelOneTile The amount of tiles
     * @param levelTwoTile The amount of tiles
     * @param levelThreeTile The amount of tiles
     */
    public Game(ArrayList<Player> playerOrder, int amountOfTurns, int levelOneTile, int levelTwoTile, int levelThreeTile) {
        this.playerOrder = playerOrder;
        this.amountOfTurns = amountOfTurns;
        this.levelOneTile = levelOneTile;
        this.levelTwoTile = levelTwoTile;
        this.levelThreeTile = levelThreeTile;
    }

    /**
     * @return levelOneTile
     */
    public int getLevelOneTile() {
        return levelOneTile;
    }

    /**
     * @param levelOneTile replaces old playerOrder
     */
    public void setLevelOneTile(int levelOneTile) {
        this.levelOneTile = levelOneTile;
    }

    /**
     * @return levelTwoTile
     */
    public int getLevelTwoTile() {
        return levelTwoTile;
    }

    /**
     * @param levelTwoTile replaces old playerOrder
     */
    public void setLevelTwoTile(int levelTwoTile) {
        this.levelOneTile = levelTwoTile;
    }

    /**
     * @return levelThreeTile
     */
    public int getLevelThreeTile() {
        return levelThreeTile;
    }

    /**
     * @param levelThreeTile replaces old playerOrder
     */
    public void setLevelThreeTile(int levelThreeTile) {
        this.levelThreeTile = levelThreeTile;
    }


    /**
     * @return playerOrder
     */
    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * @return amountOfTurns
     */
    public int getAmountOfTurns() {
        return amountOfTurns;
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
    public void createPlayer(ArrayList<String> levelOfIntelligence, int playerAmount, ArrayList<String> names,
                             ArrayList<String> colour, int figureAmount, ArrayList<Gods> gods)                  {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(levelOfIntelligence.get(i), names.get(i), colour.get(i), new ArrayList<Figure>(), new ArrayList<Gods>());
            newPlayer.addFigure(figureAmount);
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
        this.board = board;

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
     * Checks if the game ended, because of a gods ability.
     *
     * @return boardY the Height of the board
     */
    public boolean checkSpecialEnding() {
        boolean gameEnded = false;
        for (int i = 1; i < playerOrder.size(); i++) {
            Player player = playerOrder.get(i);
            ArrayList<Gods> allGods = player.getGods();
            if(allGods.contains(new Chronus())){
                if(Chronus.checkSpecialEnding()){
                    gameEnded = true;
                }
            }
            for (Gods god : allGods) {
                switch (god.getName()) {
                    case "Chronus":
                        if (Chronus.checkSpecialEnding()) {
                            gameEnded = true;
                            break;
                        }
                    case "Eros":
                        if (Eros.checkSpecialEnding()) {
                            gameEnded = true;
                            break;
                        }
                    case "Hera":
                        if (Hera.checkSpecialEnding()) {
                            gameEnded = true;
                            break;
                        }
                    case "Pan":
                        if (Pan.checkSpecialEnding()) {
                            gameEnded = true;
                            break;
                        }
                    default:
                        continue;
                }
            }
        }
        return gameEnded;
    }

    /**
     * Sabotage of the players movement abilities by other players gods.
     *
     * @param figure the figure executing the movement
     * @param possibleMovement the possible movement before the sabotage
     *
     * @return the List of fields, which are reachable after sabotage
     */
    public ArrayList<Field> sabotageMovement(Figure figure, ArrayList<Field> possibleMovement){
        for (int i = 1; i < playerOrder.size(); i++) {
            Player passivePlayer = playerOrder.get(i);
            ArrayList<Gods> passiveGods = passivePlayer.getGods();
            for(Gods god:passiveGods) {
                switch (god.getName()) {
                    case "Aphrodite":
                        possibleMovement = Aphrodite.sabotage(figure, possibleMovement);
                    case "Athena":
                        possibleMovement = Athena.sabotage(figure, possibleMovement);
                    case "Hypnus":
                        possibleMovement = Hypnus.sabotage(figure, possibleMovement);
                    case "Persephone":
                        possibleMovement = Persephone.sabotage(figure, possibleMovement);
                    default:
                        continue;
                }
            }
        }
        return possibleMovement;
    }

    /**
     * Sabotage of the players building abilities by other players gods.
     *
     * @param figure the figure executing the movement
     * @param possibleBuilds the possible builds before the sabotage
     *
     * @return the List of fields, which are buildable after sabotage
     */
    public ArrayList<Field> sabotageBuilds(Figure figure, ArrayList<Field> possibleBuilds){
        for (int i = 1; i < playerOrder.size(); i++) {
            Player passivePlayer = playerOrder.get(i);
            ArrayList<Gods> passiveGods = passivePlayer.getGods();
            for (Gods god : passiveGods) {
                switch (god.getName()) {
                    case "Limus":
                        possibleBuilds = Limus.sabotage(figure, possibleBuilds);
                    default:
                        continue;
                }
            }
        }
        return possibleBuilds;
    }

    /**
     * Runs the Game
     */
    public void runGame() {
        boolean gameEnded = false;
        while(gameEnded == false){
            Player activePlayer = playerOrder.get(0);

            // Checks the active players resources
            ArrayList<Gods> activeGods = activePlayer.getGods();
            ArrayList<Figure> activeFigures = activePlayer.getFigure();

            // Movement
            boolean artemisIsAvailable = false;
            for(Figure figure:activeFigures){
                ArrayList<Field> regularFields = sabotageMovement(figure, figure.getValidMoves(board));
                for(Gods god:activeGods) {
                    switch (god.getName()) {
                        case "Apollo":
                            ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder,figure, board);
                            apolloFields = sabotageMovement(figure, apolloFields);
                        case "Artemis":
                            artemisIsAvailable = true;
                        case "Charon":
                        case "Hermes":
                        case "Minotaures":
                        case "Triton":
                    }
                }
            }
            Field field = board.getField(0, 0);// es ist zweimal dieses stück code hier bitte eins entfernen
            Figure figure = activeFigures.get(0); // Figur und Field muss gewählt werden, hier nur Testwert
            // Artemis !!!!!!!!! muss noch angepasst werden für getvalid move
            if(artemisIsAvailable){
                int originalFigureX = figure.getX();
                int originalFigureY = figure.getY();
               // Artemis.getValidMove(figure, originalFigureX, originalFigureY);
            }

            activePlayer.executeMove(field, board, figure); //Kann auch zB. Apollo.executeMove sein

            // Building
            for(Figure figure2:activeFigures){
                ArrayList<Field> possibleFields = sabotageBuilds(figure2, figure2.getValidBuilds());
            }
            //Testweise dieses Stück rausgenommen
            //Field field = board.getField(0,0); // Feld muss aus possibleFields gewählt werden, hier nur Testwert
            activePlayer.executeBuild(field, board); // Kann auch zB. BuildingGod.executeBuild sein

            // Checks if the game is over
            this.amountOfTurns ++;
            if(checkRegularEnding()){
                gameEnded = true;
                break;
            }
            if(checkSpecialEnding()){
                gameEnded = true;
                break;
            }

            // Spielerwechsel
            Collections.rotate(playerOrder, -1);
        }
    }
}