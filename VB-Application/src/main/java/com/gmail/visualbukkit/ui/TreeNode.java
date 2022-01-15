package com.gmail.visualbukkit.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;

import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class TreeNode<T extends Node> extends StyleableVBox {

    private Label label = new Label();
    private StyleableVBox content = new StyleableVBox();
    private Comparator comparator;
    private String collapsedText;
    private String expandedText;
    private boolean expanded = true;

    public TreeNode(String labelText, Comparator<T> comparator) {
        this.comparator = comparator;
        collapsedText = "▸ " + labelText;
        expandedText = "▾ " + labelText;

        label.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                toggle();
                e.consume();
            }
        });

        getStyleClass().add("tree-node");
        getChildren().addAll(label, content);
        toggle();
    }

    public TreeNode(String labelText) {
        this(labelText, null);
    }

    public void toggle() {
        expanded = !expanded;
        label.setText(expanded ? expandedText : collapsedText);
        content.setVisible(expanded);
        content.setManaged(expanded);
    }

    public void add(T node) {
        content.getChildren().add(node);
        sort();
    }

    public void remove(T node) {
        content.getChildren().remove(node);
        sort();
    }

    public void sort() {
        if (comparator != null) {
            Node[] sorted = content.getChildren().toArray(Node[]::new);
            Arrays.sort(sorted, comparator);
            content.getChildren().setAll(sorted);
        }
    }
}
