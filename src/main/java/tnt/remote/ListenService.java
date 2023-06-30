package tnt.remote;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenService extends Service<Socket> {
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    protected Task<Socket> createTask() {
        return new Task<Socket>() {
            private ServerSocket serverSocket;

            @Override
            protected Socket call() throws IOException {
                serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                serverSocket.close();
                return socket;
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
}