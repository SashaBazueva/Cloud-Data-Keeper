package client.object;

import client.object.base.MenuElement;
import javafx.scene.layout.AnchorPane;

public class Folder extends MenuElement {
    private static final String URL = "file:src/main/resources/client/img/folder.png";
    private static int count;

    public Folder(AnchorPane pane) {
        super(pane, URL);
        text.setText("directory" + (++count));
    }
}
