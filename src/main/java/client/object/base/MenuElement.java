package client.object.base;

import client.object.Folder;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MenuElement extends Group {
    protected static final byte WIDTH = 105;
    protected static final byte HEIGHT = 105;

    private static AnchorPane pane;
    protected static Image IMG;

    protected ImageView view;
    protected TextField text;
    protected VBox vBox;

    private byte x;
    private byte y;

    public MenuElement(AnchorPane pane, String url) {
        this.pane = pane;
        IMG = new Image(url);
        vBox = new VBox(HEIGHT / 10);                          // создаю новую группу
        vBox.prefWidth(WIDTH);                                         // устанавливаю размеры
        vBox.prefHeight(HEIGHT);

        view = new ImageView(IMG);                                     //новый эллемент: изображение папки
        view.prefHeight(HEIGHT / 2);
        view.prefWidth(WIDTH / 2);

        text = new TextField();                                        // новый эллемент: название папки
        text.setPrefWidth(WIDTH);
        text.setEditable(false);
        text.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(view, text);
        vBox.setAlignment(Pos.CENTER);

        ObservableList<Node> children = pane.getChildren();             // беру список компонентов на экране
        x = (byte) ((children.size()) % 5);                             // определяю подожение папки
        y = (byte) (children.size() / 5);

        if ((y+1) * HEIGHT > pane.getMinHeight()) {
            pane.setMinHeight(pane.getMinHeight() + HEIGHT);
        }

        System.out.println(x + " " + y);
        setLayoutX(WIDTH * x);                                          // устанавливаю необходмое положение
        setLayoutY(HEIGHT * y);

        this.getChildren().add(vBox);
        pane.getChildren().add(this);
    }

    public static void delete(MenuElement element) {
        pane.getChildren().remove(element);
    }

}
