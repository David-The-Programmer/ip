package atom.ui.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


/**
 * Contains all logic for the dialog box gui component.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
    }

    private void flip() {
        dialog.getStyleClass().add("reply-label");
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Returns a user dialog box.
     *
     * @param text Text to display.
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    /**
     * Returns a atom dialog box.
     *
     * @param text Text to display.
     */
    public static DialogBox getAtomDialog(String text) {
        var db = new DialogBox(text);
        db.flip();
        return db;
    }
}
