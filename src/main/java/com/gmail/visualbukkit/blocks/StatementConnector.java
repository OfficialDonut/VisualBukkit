package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.UndoManager;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class StatementConnector extends VBox implements Consumer<StatementBlock> {

    private static StatementConnector current;
    private final StatementHolder statementHolder;

    protected StatementConnector(StatementHolder statementHolder) {
        this.statementHolder = statementHolder;
        getStyleClass().add("statement-connector");
        setDisable(true);

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            accept(e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public void accept(StatementBlock block) {
        UndoManager.current().execute(() -> {
            int oldIndex = statementHolder.getChildren().indexOf(block);
            int newIndex = statementHolder.getChildren().indexOf(this) + 1;
            if (oldIndex > 0 && oldIndex < newIndex) {
                newIndex -= 2;
            }
            block.delete();
            statementHolder.add(newIndex, block);
        });
    }

    public void show() {
        if (current != this) {
            hideCurrent();
            setDisable(false);
            current = this;
        }
    }

    public static void hideCurrent() {
        if (current != null) {
            current.setDisable(true);
            current = null;
        }
    }

    public static StatementConnector current() {
        return current;
    }
}
