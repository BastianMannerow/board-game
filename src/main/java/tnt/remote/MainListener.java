package tnt.remote;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Service class for listen on the server socket
 */
public class MainListener extends Service<Integer> {
    private int port;
    private ServerSocket serverSocket = null;
    private NetworkHandler networkHandler;



    public void setPort(int port) {
        this.port = port;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {

            @Override
            protected Integer call() throws IOException {
                try {
                    serverSocket = new ServerSocket(port);
                    networkHandler.clear();
                } catch (IOException e) {
                    System.err.println("could not start server");
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        ListenService listenService = new ListenService();
                        listenService.setNetworkHandler(networkHandler);
                        listenService.setSocket(socket);
                        listenService.setOnFailed(failedEvent -> {
                            System.err.println("Unable to start the server." );
                        });
                        listenService.start();

                    } catch (IOException e) {
                        System.err.println("I/O error: " + e);
                        System.err.println("could not accept");
                    }
                }
            }

            @Override
            protected void cancelled() {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("socket has already been closed");
                }
            }
        };
    }


    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }
}