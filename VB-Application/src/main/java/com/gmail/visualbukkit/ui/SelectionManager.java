package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.CodeBlock;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class SelectionManager {

    private static CodeBlock selected;

    static {
        VisualBukkitApp.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (selected != null && !(VisualBukkitApp.getScene().getFocusOwner() instanceof TextField)) {
                selected.handleSelectedAction(e);
            }
        });
    }

    public static void register(CodeBlock block) {
        block.getBody().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (selected != null) {
                    selected.unselect();
                }
                if (block.equals(selected)) {
                    selected = null;
                } else {
                    (selected = block).select();
                }
                e.consume();
            }
        });
    }
}
