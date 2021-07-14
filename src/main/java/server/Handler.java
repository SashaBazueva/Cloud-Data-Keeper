package server;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    private static BufferedReader in;
    private static BufferedWriter out;
    private String userName;

    public Handler(Socket client) throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = receiveMessage();
                sendMessage(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("ERROR in reading message!!!", e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Can't close connection", e);
            }
        }
    }

    private String extractUserName(String message) {

        String tmp;
        if (message.trim().length() > 10) {
            tmp = message.substring(0, 11); //имя пользователя может быть длинной в 10 символов + 1 символ на двоеточие
        } else {
            tmp = message;
        }

        char[] chars = tmp.toCharArray();
        int index = 1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ':') {
                index = i;
                break;
            }
        }
        return message.substring(0, index);
    }

    private String receiveMessage() throws IOException {
        String message = in.readLine();
        userName = extractUserName(message);
        message = message.replace(userName + ": ", "");
        return message;
    }

    private void sendMessage(String message) throws IOException {
        if (!message.trim().isEmpty()) {
            System.out.println(userName + ": " + message);
            System.out.println("Server: " + message);
            out.write(message + "\n");
            out.flush();
        }
    }
}
