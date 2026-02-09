package atom.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private Label text;

    public DialogBox(String s) {
        text = new Label(s);
        text.setWrapText(true);
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text);
    }
}
