package client;

import java.io.*;
import java.net.Socket;

public class ClientSession {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ClientSession(String host, int port) {

        try {
            socket = new Socket(host, port);
            System.out.println("Соединение установленно!");
        } catch (IOException e) {
            throw new RuntimeException("Server is offline", e);
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Успешное подкдлючение к серверу!!");

        }catch(IOException e){
            throw new RuntimeException("Can't connect to the server");
        }
    }

    public void sendMessage(String message) {
        try {
            if (!message.trim().isEmpty()) {
                message = "You: " + message;
                System.out.println(message);
                out.write(message + "\n");
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't send message");
        }
    }

    public String receiveMessage() {
        String serverMessage;
        try {
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);
            return serverMessage;
        } catch (IOException e) {
            throw new RuntimeException("Can't receive message");
        }
    }

    public void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't close connection", e);
        }
    }

}
