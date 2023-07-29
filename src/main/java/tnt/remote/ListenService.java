package tnt.remote;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Service class for starting the connection to the client
 */
public class ListenService extends Service<Integer> {
    private NetworkHandler networkHandler;
    private Socket socket;

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            private ServerSocket serverSocket;

            @Override
            protected Integer call() throws IOException {
                networkHandler.startServerClientConnection(socket);
                return 0;
            }

            @Override
            protected void cancelled() {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // ignore
                    System.err.println("Socket has been closed already");
                }
            }
        };
    }

    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}