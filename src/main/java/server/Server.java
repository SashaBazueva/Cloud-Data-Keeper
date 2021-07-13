package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server;
    private static int count;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(4040);
            System.out.println("Server is running...");
        } catch (IOException e) {
            throw new RuntimeException("Port is already taken", e);
        }

        try {
            while (true) {
                System.out.println("Server is waiting for connection...");
                Socket client = server.accept();
                System.out.println("Connection is occurred!");

                new Thread(new Handler(client)).start();
                System.out.println("Connection is established");
                System.out.println("Active connection: " + ++count);
                System.out.println("--------");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't connect to the client");
        } finally {
            try {
                server.close();
                System.out.println("Server is closed");
            } catch (IOException e) {
                throw new RuntimeException("Can't close server", e);
            }

        }
    }
}
