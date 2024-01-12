package com.gmail.visualbukkit.blocks;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PluginComponentPane extends ScrollPane {

    private final VBox content = new VBox();
    private final Region spacer = new Region();
    private PluginComponentBlock block;

    public PluginComponentPane() {
        getStyleClass().add("plugin-component-pane");
        setContent(content);
        spacer.setPrefHeight(1000);

        content.setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                if (e.getY() < this.block.getChildStatementHolder().getBoundsInParent().getMinY()) {
                    this.block.getChildStatementHolder().showFirstConnector();
                } else {
                    this.block.getChildStatementHolder().showLastConnector();
                }
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        content.setOnDragDropped(e -> {
            StatementBlock statementBlock = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.current().accept(statementBlock);
            e.setDropCompleted(true);
            e.consume();
        });

        content.setOnDragExited(e -> StatementConnector.hideCurrent());
    }

    public void setBlock(PluginComponentBlock block) {
        this.block = block;
        content.getChildren().setAll(block, block.getChildStatementHolder(), spacer);
    }

    public PluginComponentBlock getBlock() {
        return block;
    }
}
