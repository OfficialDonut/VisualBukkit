package com.gmail.visualbukkit.blocks;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PluginComponentPane extends ScrollPane {

    public PluginComponentPane(PluginComponentBlock block) {
        getStyleClass().add("plugin-component-pane");
        Region spacer = new Region();
        spacer.setPrefHeight(1000);
        VBox content = new VBox(block, block.getStatementHolder(), spacer);
        setContent(content);

        content.setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                if (e.getY() < block.getStatementHolder().getBoundsInParent().getMinY()) {
                    block.getStatementHolder().showFirstConnector();
                } else {
                    block.getStatementHolder().showLastConnector();
                }
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        content.setOnDragDropped(e -> {
            StatementBlock statementBlock = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.getCurrent().accept(statementBlock);
            e.setDropCompleted(true);
            e.consume();
        });

        content.setOnDragExited(e -> StatementConnector.hideCurrent());
    }
}
