package com.gmail.visualbukkit.ui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Comparator;

public class ButtonVBox extends VBox {

    public ButtonVBox(Button... buttons) {
        super(buttons);
        getStyleClass().add("button-vbox");
        Platform.runLater(() -> {
            Arrays.stream(buttons).max(Comparator.comparingDouble(Region::getWidth)).ifPresent(longestButton -> {
                for (Button button : buttons) {
                    if (!button.equals(longestButton)) {
                        button.prefWidthProperty().bind(longestButton.widthProperty());
                    }
                }
            });
        });
    }
}
