package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.CodeBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.WeakHashMap;

public class ElementInspector extends ScrollPane {

    private ScrollPane scrollPane = new ScrollPane();
    private VBox unselectedVbox = new VBox();
    private Map<Inspectable, Pane> inspectionPanes = new WeakHashMap<>();
    private Inspectable currentElement;

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

        VisualBukkit.getInstance().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (currentElement instanceof CodeBlock) {
                CodeBlock block = (CodeBlock) currentElement;
                KeyCode key = e.getCode();
                if (e.isShortcutDown()) {
                    if (key == KeyCode.C) {
                        block.copy();
                    } else if (key == KeyCode.X) {
                        block.cut();
                    }
                } else if (key == KeyCode.DELETE) {
                    block.delete();
                }
            }
        });
    }

    public void inspect(Inspectable inspectable) {
        if (currentElement != null) {
            currentElement.unhighlight();
        }
        currentElement = inspectable;
        scrollPane.setContent(inspectionPanes.computeIfAbsent(inspectable, k -> inspectable.createInspectorPane()));
        inspectable.highlight();
    }

    public void uninspect() {
        if (currentElement != null) {
            currentElement.unhighlight();
        }
        scrollPane.setContent(unselectedVbox);
    }

    public interface Inspectable {

        Pane createInspectorPane();

        default void highlight() {}

        default void unhighlight() {}
    }
}
