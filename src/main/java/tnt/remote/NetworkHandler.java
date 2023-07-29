package tnt.remote;

import javafx.scene.control.TextInputDialog;
import tnt.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Network handler for handling the network connections
 */
public class NetworkHandler {
    private static final int PORT_NUMBER = 4444;
    private List<PrintWriter> networkPrinter = new ArrayList<>();

    /**
     * starts the thread to listen on a socket for clients to connect
     */
    public void listen() {
        MainListener mainListener = new MainListener();
        mainListener.setPort(PORT_NUMBER);
        mainListener.setNetworkHandler(this);
        mainListener.setOnFailed(failedEvent -> {
            System.out.println("Failed server socket thread" );
        });
        mainListener.setOnSucceeded(succeededEvent -> {
            System.out.println("Closed server thread");
        });
        mainListener.start();

    }

    void startServerClientConnection(Socket socket) {
        System.out.println("Server: Client connected");
        try {
            initConnection(socket);
            Settings.setServerMode(true);
        } catch (IOException e) {
//            error("Server: Error opening connecting", e.getMessage());
//            reset();
            System.err.println("could not establish server socket");
        }
    }


    private void initConnection(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ReadLineService readLineService = new ReadLineService();
        readLineService.setBufferedReader(bufferedReader);

        networkPrinter.add(new PrintWriter(socket.getOutputStream(), true));

        readLineService.setOnFailed(failedEvent -> {
//            reset();
//            toolbar.getItems().remove(closeButton);
        });
        readLineService.setOnSucceeded(readEvent -> {
            String line = (String) readEvent.getSource().getValue();
            receiveMsg(line);
        });

        readLineService.start();
    }

    /**
     * start connection to a server
     * @param url the address of the server
     */
    public void startClient(String url) {
        try {
            Socket socket = new Socket(url, PORT_NUMBER);
            initConnection(socket);
            Settings.setServerMode(false);
        } catch (IOException e) {
            System.err.println("Error connecting to the server");
//            error("Error connecting to the server", e.getMessage());
//            reset();
        }
    }

    /**
     * sends a message to all network parnter
     * @param msg the message to get send
     */
    public void sendMsg(String msg) {
        String msgnew = msg.replace("\n", "\\n");
        for(PrintWriter pw: networkPrinter) {
            pw.println(msgnew);
        }
    }

    private void receiveMsg(String line) {
        String msg = line.replace("\\n", "\n");
        FileManager fm = new FileManager();
        switch (msg.substring(0,4)){
            case "game":
                parseGame(fm, msg.substring(4));
                break;
            case "play":
                break;
            case "fiel":
                break;
            case "move":
                parseMove(msg.substring(4));
                break;
            case "buil":
                parseBuild(msg.substring(4));
                break;
            default:

        }
    }

    private void parseGame(FileManager fm, String gameStr){
        String[] data = gameStr.split("new####data");
        List<String[]> gameData = fm.readString(data[0]);
        List<String[]> playerData = fm.readString(data[1]);
        List<String[]> figureData = fm.readString(data[2]);
        List<String[]> fieldsData = fm.readString(data[3]);
        List<String[]> boardData = fm.readString(data[4]);
        Game game = new Game(0);
//        game.setAmountOfTurns(Integer.parseInt(gameData.get(0)[2]));
        // Todo: set tiles for each player and everything else


        Settings.setActualGame(game);
    }

    private void parseBuild(String buildStr){
        String[] buildData = buildStr.split(",");
        try {
            Field field = Settings.getActualGame().getBoard().getField(Integer.parseInt(buildData[1]), Integer.parseInt(buildData[2]));
            ExecuteGameInputs.buildObject(Integer.parseInt(buildData[0]), field);
        } catch (NumberFormatException e) {
            System.err.println("Could not build object, because integer could not get parsed: " + buildData[0] + ", " + buildData[1] + ", " + buildData[2] + ", " + e);
//                    throw new RuntimeException(e);
        }
    }

    private void parseMove(String moveStr){
        String[] moveData = moveStr.split(",");
        try {
            Figure figure = null;
            if (Boolean.parseBoolean(moveData[2])) {
                Field fieldSrc = Settings.getActualGame().getBoard().getField(Integer.parseInt(moveData[0]), Integer.parseInt(moveData[1]));
                figure = fieldSrc.getFigure();
            } else {
                for (Figure fig: Settings.getActualGame().getPlayersTurn().getFigure()){
                    if (!fig.isPlaced()){
                        figure = fig;
                        break;
                    }
                }
            }
            Field fieldDest = Settings.getActualGame().getBoard().getField(Integer.parseInt(moveData[3]), Integer.parseInt(moveData[4]));
            if (figure != null) {
                ExecuteGameInputs.placeFigure(figure, fieldDest);
            }
        } catch (NumberFormatException e) {
            System.err.println("Could not move figure, because integer could not get parsed: " + moveData[0] + ", " + moveData[1] + ", " + moveData[2] + ", " + e);
//                    throw new RuntimeException(e);
        }
    }

    /**
     * sends the message of placing a figure to all network partner
     * @param figure the figure to get placed
     * @param field the field, the figure should be placed on
     */
    public void place(Figure figure, Field field) {
        String msg = "move" + figure.getX() + "," + figure.getY() + "," + figure.isPlaced() + "," + field.getX() + "," + field.getY();
        sendMsg(msg);
    }

    /**
     * sends the build message to all network partner
     * @param buildLevel the level to build
     * @param field the field to build on
     */
    public void build(int buildLevel, Field field) {
        String msg = "buil" + buildLevel + "," + field.getX() + "," + field.getY();
        sendMsg(msg);
    }

    /**
     * send the game to all connected network partner
     * @param actualGame
     */
    public void sendGame(Game actualGame) {
        System.out.println("Sending here the game");
        // Get Game Information
        FileManager fm = new FileManager();
        StringBuilder data = new StringBuilder();
        List<String[]> gameData = fm.getGameData(actualGame);
        data.append(fm.makeString(gameData));
        data.append("new####data");

        // Get Player Information
        ArrayList<Player> playerList = actualGame.getPlayerOrder();
        List<String[]> playerData = fm.getPlayersData(playerList);
        data.append(fm.makeString(playerData));
        data.append("new####data");

        // Get Figure Information
        List<String[]> figureData = fm.getFiguresData(playerList);
        data.append(fm.makeString(figureData));
        data.append("new####data");

        // Get Field Information
        List<String[]> fieldsData = fm.getFieldsData(actualGame);
        data.append(fm.makeString(fieldsData));
        data.append("new####data");

        // Get Board Information
        List<String[]> boardData = fm.getBoardData(actualGame);
        data.append(fm.makeString(boardData));
        sendMsg("game" + data);
//        System.out.println("sendet this game: " + data.toString());
    }

    /**
     * makes a dialog for asking for the address of the server
     * @return the ip/host name of the server
     */
    public Optional<String> askForHost() {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("Network address");
        dialog.setHeaderText("Connecting to a TNT server");
        dialog.setContentText("Enter the servers' IP address or host name:");
        return dialog.showAndWait();
    }

    /**
     * closes all connections
     */
    public void clear() {
        for (PrintWriter pw: networkPrinter) {
            pw.close();
        }
        networkPrinter.clear();
    }
}