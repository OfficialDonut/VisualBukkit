package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.UndoManager;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatementSelector extends VBox {

    private final VBox statementListBox = new VBox();

    public StatementSelector() {
        getStyleClass().add("statement-selector");

        TextField searchField = new TextField();

        getChildren().addAll(new VBox(new HBox(new Label(VisualBukkitApp.localizedText("label.search")), searchField)), new Separator(), new ScrollPane(statementListBox));

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementBlock || e.getGestureSource() instanceof ExpressionBlock) {
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            UndoManager.execute(e.getGestureSource() instanceof StatementBlock s ? s.delete() : ((ExpressionBlock) e.getGestureSource()).delete());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public void refreshStatements() {
        for (StatementBlock.Factory factory : BlockRegistry.getStatements()) {
            statementListBox.getChildren().add(new StatementSource(factory));
        }
    }
}
