package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;
import tnt.util.Observable;
import tnt.model.interfaces.Gods;
import tnt.model.gods.movement.*;

/**
 * The Game class, which is responsible for general mechanics during the Game.
 */
public class Game extends Observable {

    public enum GameStatus {
        SELECT_PLAYER,
        PLACE_FIGURES,
        MOVE_FIGURE,
        BUILD
    }

    private Color[] def_colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.PINK};
    private ArrayList<Player> playerOrder;
    private Board board;
    private int amountOfTurns;


    private Figure lastMovedFigure;
    private GameStatus gameStatus;

    /**
     * Constructing an object Game.
     * @param playerOrder
     */
    public Game(ArrayList<Player> playerOrder, int amountOfTurns) {
        this.playerOrder = playerOrder;
        this.amountOfTurns = amountOfTurns;
    }

    public Game(int amountOfTurns) {
        this.playerOrder = new ArrayList<Player>();
        this.amountOfTurns = amountOfTurns;
    }

    public Game() {
        gameStatus = GameStatus.SELECT_PLAYER;
        this.playerOrder = new ArrayList<Player>();
        // Todo: get default amount of players
        addPlayer(2);
        addPlayer(2);
//        createBoard(5,6);
    }

    public void initGame() {
        for(Player player: playerOrder){
            player.initPlayer();
        }
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
    public void createPlayer(ArrayList<String> levelOfIntelligence, int playerAmount, ArrayList<String> names, ArrayList<String> colour, int figureAmount, ArrayList<Gods> gods) {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(levelOfIntelligence.get(i), names.get(i), Color.RED, new ArrayList<Figure>(), gods);
            newPlayer.addFigure(figureAmount);
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }
    public void addPlayer(Player player) {
        playerOrder.add(player);
        System.out.println("added Player");
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
        notifyObservers();
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
//    public boolean checkSpecialEnding() {
//        boolean gameEnded = false;
//        for (int i = 1; i < playerOrder.size(); i++) {
//            Player player = playerOrder.get(i);
//            ArrayList<Gods> allGods = player.getGods();
//
//            for (Gods god : allGods) {
//                switch (god) {
//                    case Chronus:
//                        if (Chronus.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case Eros:
//                        if (Eros.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case Hera:
//                        if (Hera.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case Pan:
//                        if (Pan.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    default:
//                        continue;
//                }
//            }
//        }
//        return gameEnded;
//    }

    /**
     * Sabotage of the players movement abilities by other players gods.
     *
     * @param figure the figure executing the movement
     * @param possibleMovement the possible movement before the sabotage
     *
     * @return the List of fields, which are reachable after sabotage
     */
    public ArrayList<Field> sabotageMovement(Figure figure, ArrayList<Field> possibleMovement){
//        for (int i = 1; i < playerOrder.size(); i++) {
//            Player passivePlayer = playerOrder.get(i);
//            ArrayList<Gods> passiveGods = passivePlayer.getGods();
//            for(Gods god:passiveGods) {
//                switch (god) {
//                    case Aphrodite:
//                        possibleMovement = Aphrodite.sabotage(figure, possibleMovement);
//                    case Athena:
//                        possibleMovement = Athena.sabotage(figure, possibleMovement);
//                    case Hypnus:
//                        possibleMovement = Hypnus.sabotage(figure, possibleMovement);
//                    case Persephone:
//                        possibleMovement = Persephone.sabotage(figure, possibleMovement);
//                    default:
//                        continue;
//                }
//            }
//        }
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
//            for (Gods god : passiveGods) {
//                switch (god) {
//                    case Limus:
//                        possibleBuilds = Limus.sabotage(figure, possibleBuilds);
//                    default:
//                        continue;
//                }
//            }
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
//            for(Figure figure:activeFigures){
//                ArrayList<Field> regularFields = sabotageMovement(figure, figure.getValidMoves(board));
//                for(Gods god:activeGods) {
//                    switch (god) {
//                        case Apollo:
//                            ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder, board);
//                            apolloFields = sabotageMovement(figure, apolloFields);
//                        case Artemis:
//                            artemisIsAvailable = true;
//                        case Charon:
//                        case Hermes:
//                        case Minotaures:
//                        case Triton:
//                    }
//                }
//            }
            Field field = board.getField(0, 0);// es ist zweimal dieses stück code hier bitte eins entfernen
            Figure figure = activeFigures.get(0); // Figur und Field muss gewählt werden, hier nur Testwert
            // Artemis
            if(artemisIsAvailable){
                int originalFigureX = figure.getX();
                int originalFigureY = figure.getY();
                Artemis.getValidMove(figure, originalFigureX, originalFigureY);
            }

            activePlayer.executeMove(field, board, figure); //Kann auch zB. Apollo.executeMove sein


            // Building
            for(Figure figure2:activeFigures){
                ArrayList<Field> possibleFields = sabotageBuilds(figure2, figure2.getValidBuilds(board));
            }

            Field field2 = board.getField(0,0); // Feld muss aus possibleFields gewählt werden, hier nur Testwert
            activePlayer.executeBuild(field2, board); // Kann auch zB. BuildingGod.executeBuild sein

            // Checks if the game is over
            this.amountOfTurns ++;
            if(checkRegularEnding()){
                gameEnded = true;
                break;
            }
//            if(checkSpecialEnding()){
//                gameEnded = true;
//                break;
//            }

            // Spielerwechsel
            nextPlayersTurn();
        }
    }

    /**
     * Checks if the Game is over
     */
    public boolean checkEnd(boolean gameEnded){
        // Differenzierung 2 oder 4 Spieler einbauen.
        return gameEnded;
    }

    public Player getPlayersTurn(){
        if (playerOrder.isEmpty()){
            return null;
        }
        return playerOrder.get(0);
    }

    public boolean isRunnung(){
        return gameStatus == GameStatus.BUILD || gameStatus == GameStatus.MOVE_FIGURE;
    }

    public boolean isMoveMode(){
        return gameStatus == GameStatus.MOVE_FIGURE;
    }


    public void setBuildMode() {
        gameStatus = GameStatus.BUILD;
    }

    public void setMoveMode() {
        gameStatus = GameStatus.MOVE_FIGURE;
    }

    public boolean isBuildMode(){
        return gameStatus == GameStatus.BUILD;
    }

    public boolean selectingPlayers(){
        return gameStatus == GameStatus.SELECT_PLAYER;
    }

    public void startPlaceFigures(){
        gameStatus = GameStatus.PLACE_FIGURES;
    }
    public void startGame(){
        gameStatus = GameStatus.MOVE_FIGURE;
        notifyObservers();
    }


    public void addPlayer(int amountOfFigures) {
        playerOrder.add(new Player("" , "Player " + (playerOrder.size()+1), def_colors[playerOrder.size() % def_colors.length], amountOfFigures, this));
        notifyObservers();
    }

    public void removePlayer(Player player) {
        playerOrder.remove(player);
        notifyObservers();
    }

    public Board getBoard(){
        return this.board;
    }

    public boolean placeFigures(){
        return gameStatus == GameStatus.PLACE_FIGURES;
    };


    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void nextPlayersTurn(){
        Collections.rotate(playerOrder, -1);
//        System.out.println("Now its " + getPlayersTurn().getName() + "turn.");
        notifyObservers();
    }

    public void setLastMovedFigure(Figure figure) {
        lastMovedFigure = figure;
    }

    public Figure getLastMovedFigure() {
        return lastMovedFigure;
    }


}