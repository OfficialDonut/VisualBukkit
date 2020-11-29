package com.gmail.visualbukkit.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.WeakHashMap;

public class ElementInspector extends ScrollPane {

    private ScrollPane scrollPane = new ScrollPane();
    private VBox unselectedVbox = new VBox();
    private Map<Inspectable, Pane> inspectionPanes = new WeakHashMap<>();

    public ElementInspector() {
        Label titleLabel = new Label("Element Inspector");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));
        titleLabel.setUnderline(true);

        unselectedVbox.setAlignment(Pos.CENTER);
        unselectedVbox.getChildren().add(new Label("<no element selected>"));

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleLabel);
        borderPane.setCenter(scrollPane);
        borderPane.setPadding(new Insets(10));

        setContent(borderPane);
        setFitToWidth(true);
        setFitToHeight(true);
        uninspect();
    }

    public void inspect(Inspectable inspectable) {
        scrollPane.setContent(inspectionPanes.computeIfAbsent(inspectable, k -> inspectable.createInspectorPane()));
    }

    public void uninspect() {
        scrollPane.setContent(unselectedVbox);
    }

    public interface Inspectable {

        Pane createInspectorPane();
    }
}
