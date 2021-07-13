import java.io.*;
import java.net.Socket;

public class Client {

    private static BufferedReader in;
    private static BufferedWriter out;
    private static BufferedReader consoleReader;

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("localhost", 4040);
            System.out.println("Подключение установленно!");
        } catch (IOException e) {
            throw new RuntimeException("Server is offline", e);
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Напишите ваше сообщение: ");
            while (true) {
                String message = consoleReader.readLine();
                if (!message.trim().isEmpty()) {
                    message = "Client: " + message + "\n";
                    out.write(message);
                    out.flush();
                    String serverMessage = in.readLine();
                    System.out.println("Server: " + serverMessage);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't connect to the server", e);
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
                consoleReader.close();
            } catch (IOException e) {
                throw new RuntimeException("Can't close connection", e);
            }
        }
    }
}
