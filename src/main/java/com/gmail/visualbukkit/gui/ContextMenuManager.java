package com.gmail.visualbukkit.gui;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;

public class ContextMenuManager {

    private static ContextMenu currentContextMenu;

    public static void show(Node node, ContextMenu contextMenu, ContextMenuEvent e) {
        show(node, contextMenu, e.getScreenX(), e.getScreenY());
        e.consume();
    }

    public static void show(Node node, ContextMenu contextMenu, double screenX, double screenY) {
        hide();
        currentContextMenu = contextMenu;
        contextMenu.show(node, screenX, screenY);
    }

    public static void hide() {
        if (currentContextMenu != null) {
            currentContextMenu.hide();
        }
    }
}
