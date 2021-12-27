package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockNode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class SelectionManager {

    private static BlockNode selected;

    static {
        VisualBukkitApp.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (selected != null && !(VisualBukkitApp.getScene().getFocusOwner() instanceof TextField)) {
                selected.handleSelectedAction(e);
            }
        });
    }

    public static void register(BlockNode block) {
        block.getBody().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (selected != null) {
                    selected.setSelected(false);
                }
                if (block.equals(selected)) {
                    selected = null;
                } else {
                    (selected = block).setSelected(true);
                }
                e.consume();
            }
        });
    }

    public static void select(BlockNode block) {
        if (selected != null) {
            selected.setSelected(false);
        }
        (selected = block).setSelected(true);
    }
}
