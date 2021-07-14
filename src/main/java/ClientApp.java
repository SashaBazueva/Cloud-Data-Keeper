import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientApp extends Application {
    private static final int MAX_STRING_COUNT = 100;

    private static StringBuilder sb;
    private static ClientSession session;
    private static ArrayList<String> strings;

    public TextArea textArea;
    public TextField textField;
    public Button button;

    @Override
    public void start(Stage primaryStage) throws Exception {
        textArea = new TextArea("\n");
        textField = new TextField();
        button = new Button();

        session = new ClientSession("localhost", 4040);
        strings = new ArrayList<>(MAX_STRING_COUNT);
        sb = new StringBuilder();

        Parent root = FXMLLoader.load(getClass().getResource("ClientApp.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        session.closeConnection();
        super.stop();
    }

    public void enter(ActionEvent actionEvent) {
        String message = textField.getText();
        textField.clear();
        session.sendMessage(message);
        updateTextArea("You: " + message);
        String serverMessage = session.receiveMessage();
        updateTextArea("Server: " + serverMessage);
    }

    private void rememberMessage(String message) {
        strings.add(message);
        if (strings.size() > MAX_STRING_COUNT) {
            strings.remove(0);
        }
    }

    private String outputMessages() {
        sb.setLength(0);
        for (int i = strings.size() - 1; i > 0; i--) {
            sb.append(strings.get(i) + "\n");
        }
        sb.append(strings.get(0));
        return sb.toString();
    }

    private void updateTextArea(String message){
        rememberMessage(message);
        textArea.setText(outputMessages());
    }
}
