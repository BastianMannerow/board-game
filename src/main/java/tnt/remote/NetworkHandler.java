package tnt.remote;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkHandler {
    private static final int PORT_NUMBER = 4444;

    private PrintWriter networkPrinter;

    private void listen() {
        ListenService listenService = new ListenService();
        listenService.setPort(PORT_NUMBER);
        Button cancelButton = new Button("Cancel");
        listenService.setOnFailed(failedEvent -> {
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

    private void sendMsg(String msg) {
        networkPrinter.println(msg);
    }

    private void receiveMsg(String line) {
        System.out.println("Received: " + line);
    }



}
