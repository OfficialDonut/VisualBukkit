package com.gmail.visualbukkit.util;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TreeNode extends VBox {

    private final static String RIGHT_ARROW = "▸ ";
    private final static String DOWN_ARROW = "▾ ";
    private VBox content = new VBox(5);
    private Label label;
    private String labelText;
    private boolean expanded;

    public TreeNode(String labelText) {
        super(5);
        label = new Label(RIGHT_ARROW + (this.labelText = labelText));
        label.setOnMouseClicked(e -> toggle());
        content.setPadding(new Insets(0, 0, 0, 15));
        content.setVisible(false);
        content.setManaged(false);
        getChildren().addAll(label, content);
    }

    public void add(Node... nodes) {
        content.getChildren().addAll(nodes);
    }

    public void remove(Node node) {
        content.getChildren().remove(node);
    }

    public void clear() {
        content.getChildren().clear();
    }

    public void toggle() {
        label.setText((expanded ? RIGHT_ARROW : DOWN_ARROW) + labelText);
        expanded = !expanded;
        content.setVisible(expanded);
        content.setManaged(expanded);
    }
}
