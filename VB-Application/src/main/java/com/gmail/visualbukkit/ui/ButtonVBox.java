package com.gmail.visualbukkit.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Comparator;

public class ButtonVBox extends VBox {

    public ButtonVBox(Button... buttons) {
        super(buttons);
        getStyleClass().add("button-vbox");
    }

    public void bindSizes() {
        ObservableList<Button> buttons = (ObservableList<Button>) (Object) getChildren();
        buttons.stream().max(Comparator.comparingDouble(Region::getWidth)).ifPresent(longestButton -> {
            for (Button button : buttons) {
                if (!button.equals(longestButton)) {
                    button.prefWidthProperty().bind(longestButton.widthProperty());
                }
            }
        });
    }
}
