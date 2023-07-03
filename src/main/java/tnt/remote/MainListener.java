package tnt.remote;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
                } catch (IOException e) {
                    System.out.println("could not start server");
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        System.out.println("waiting for client");
                        Socket socket = serverSocket.accept();
                        System.out.println("client connected oho");

                        ListenService listenService = new ListenService();
                        listenService.setNetworkHandler(networkHandler);
                        listenService.setSocket(socket);
                        listenService.setOnFailed(failedEvent -> {
                            System.out.println("Unable to start the server." );
                        });

                        listenService.setOnSucceeded(succeededEvent -> {
                            System.out.println("Finished with that client");
                        });
                        listenService.start();

                    } catch (IOException e) {
                        System.out.println("I/O error: " + e);
                        System.out.println("could not accept");
                    }
                }
//                return 0;
            }

            @Override
            protected void cancelled() {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        };
    }


    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }
}