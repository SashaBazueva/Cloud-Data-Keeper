package client;

import client.object.Folder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;

public class ClientApp extends Application {
    private static final ClientSession session = new ClientSession("localhost", 4040);
    private static String userName = "U$er2764"; //должен получать  имя пользователся при лог ине

    public AnchorPane mainAnchorPane;

    public Label path;
    public Group workWindow;

    public HBox hBox;
    public ScrollPane scrollPane;

    public Button create;
    public Button add;
    public AnchorPane anchorPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        session.login(userName);
        mainAnchorPane = FXMLLoader.load(new URL("file:src/main/resources/client/ClientApp.fxml"));
        path = (Label) mainAnchorPane.getChildren().get(0);
        path.setText("Path: /" + userName);
        workWindow = (Group) mainAnchorPane.getChildren().get(2);
        hBox = (HBox) workWindow.getChildren().get(0);
        scrollPane = (ScrollPane) workWindow.getChildren().get(1);
        create = (Button) hBox.getChildren().get(0);
        add = (Button) hBox.getChildren().get(1);
        anchorPane = (AnchorPane) scrollPane.getContent();

        Scene scene = new Scene(mainAnchorPane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        session.closeConnection();
        mainAnchorPane.getChildren().clear();
        hBox.getChildren().clear();
        anchorPane.getChildren().clear();
    }

    public void create(ActionEvent actionEvent) {
        Folder folder = new Folder(anchorPane);
    }

    public void showContextMenu(ContextMenuEvent contextMenuEvent) {
    }
}
