package client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static BufferedReader consolReader;
    private static StringBuilder sb;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 4040);
            System.out.println("Подключение установленно!");
        } catch (IOException e) {
            throw new RuntimeException("Server is offline", e);
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            consolReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Напишите ваше сообщение: ");
            while (true) {
                String message = consolReader.readLine();
                out.write("Client: " + message + "\n");
                out.flush();
                String serverMessage = in.readLine();
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't connect to the server", e);
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Can't close connection", e);
            }
        }

    }
}
