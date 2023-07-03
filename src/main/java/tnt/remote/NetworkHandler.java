package tnt.remote;

import javafx.scene.control.Button;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class NetworkHandler {
    private static final int PORT_NUMBER = 4444;

    private PrintWriter networkPrinter;

    public void listen() {
        ListenService listenService = new ListenService();
        listenService.setPort(PORT_NUMBER);
        Button cancelButton = new Button("Cancel");
        listenService.setOnFailed(failedEvent -> {
            System.out.println("Unable to start the server." );
//            Throwable e = failedEvent.getSource().getException();
//            error("Unable to start the server.", e.getMessage());
//            toolbar.getItems().remove(cancelButton);
//            reset();
        });
//        statusBar.setText("Waiting for client to connect");
//        cancelButton.setOnMouseClicked(event -> {
//            listenService.cancel();
//            toolbar.getItems().remove(cancelButton);
//            reset();
//        });
//        toolbar.getItems().add(cancelButton);
        listenService.setOnSucceeded(succeededEvent -> {
            Socket socket = (Socket) succeededEvent.getSource().getValue();
//            toolbar.getItems().remove(cancelButton);
            startServer(socket);
        });
        listenService.start();
    }

    private void startServer(Socket socket) {
//        statusBar.setText("Server: Client connected");
        System.out.println("Server: Client connected");
        try {
            initConnection(socket);
        } catch (IOException e) {
//            error("Server: Error opening connecting", e.getMessage());
//            reset();
        }
    }


    private void initConnection(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ReadLineService readLineService = new ReadLineService();
        readLineService.setBufferedReader(bufferedReader);

//        Button closeButton = new Button("Close Connection");
//        closeButton.setOnMouseClicked(event -> {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                // ignore
//            }
//        });
//        toolbar.getItems().add(closeButton);

        networkPrinter = new PrintWriter(socket.getOutputStream(), true);
//        connected = true;
//        updateCircles();

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

    public void startClient(String s) {
//        hideDefaultButtons();
//        statusBar.setText("Client: Connected to server");
        try {
            Socket socket = new Socket("localhost", PORT_NUMBER);
            initConnection(socket);
        } catch (IOException e) {
            System.out.println("Error connecting to the server");
//            error("Error connecting to the server", e.getMessage());
//            reset();
        }
    }

    public void sendMsg(String msg) {
        networkPrinter.println(msg.replace("\n", "\\n"));
//        networkPrinter.println(msg);
    }

    private void receiveMsg(String line) {
        String msg = line.replace("\\n", "\n");
//        System.out.println("Received: " + line.replace("\\n", "\n"));
//        System.out.println("Received: " + line);
        FileManager fm= new FileManager();
        List<String[]> gameData = fm.readString(msg);
        Game game = new Game();
        game.setAmountOfTurns(Integer.parseInt(gameData.get(0)[2]));
        game.setLevelOneTile(Integer.parseInt(gameData.get(0)[3]));
        game.setLevelTwoTile(Integer.parseInt(gameData.get(0)[4]));
        game.setLevelThreeTile(Integer.parseInt(gameData.get(0)[5]));
        game.setLevelFourTile(Integer.parseInt(gameData.get(0)[6]));
        Settings.setActualGame(game);
    }



}
