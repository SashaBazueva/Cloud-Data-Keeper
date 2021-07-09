package server;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    private Socket client;
    private static BufferedReader in;
    private static BufferedWriter out;
    private StringBuilder sb;

    public Handler(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        sb = new StringBuilder();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                System.out.println(message);
                message = "Server: " + message.replace("Client: ", "") + "\n";
                System.out.println(message.replace("\n", ""));
                out.write(message);
                out.flush();
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
}
