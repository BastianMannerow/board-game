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
                System.out.println("waiting for clinet");
                Socket socket = serverSocket.accept();
                System.out.println("client connected oho");
                serverSocket.close(); // Todo: dont close the socket or recrate it?
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