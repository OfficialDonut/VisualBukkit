package us.donut.visualbukkit.util;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class ResizingComboBox<T> extends ComboBox<T> {

    public ResizingComboBox() {
        valueProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            Text text = new Text(getConverter().toString(newValue));
            setPrefWidth(text.getLayoutBounds().getWidth() + 55);
        }));
    }
}
