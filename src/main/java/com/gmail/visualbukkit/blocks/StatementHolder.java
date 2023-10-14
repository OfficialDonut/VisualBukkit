package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.project.UndoManager;
import javafx.scene.layout.VBox;

import java.util.Iterator;
import java.util.StringJoiner;

public class StatementHolder extends VBox implements Iterable<StatementBlock> {

    private final StatementConnector initialConnector = new StatementConnector(this);

    public StatementHolder() {
        getStyleClass().add("statement-holder");
        getChildren().add(initialConnector);
    }

    public void showFirstConnector() {
        initialConnector.show();
    }

    public void showLastConnector() {
        ((StatementConnector) getChildren().get(getChildren().size() - 1)).show();
    }

    public void showConnector(StatementBlock block, boolean after) {
        ((StatementConnector) getChildren().get(getChildren().indexOf(block) + (after ? 1 : -1))).show();
    }

    public UndoManager.RevertibleAction addLast(StatementBlock block) {
        return add(getChildren().size(), block);
    }

    public UndoManager.RevertibleAction add(int index, StatementBlock block) {
        int oldIndex = getChildren().indexOf(block);
        int newIndex = oldIndex > 0 && oldIndex < index ? index - 2 : index;
        return new UndoManager.RevertibleAction() {
            private UndoManager.RevertibleAction deleteAction;
            @Override
            public void execute() {
                (deleteAction = block.delete()).execute();
                getChildren().add(newIndex, block);
                getChildren().add(newIndex + 1, new StatementConnector(StatementHolder.this));
                ProjectManager.getCurrentProject().updateBlockStates();
            }
            @Override
            public void revert() {
                getChildren().remove(newIndex, newIndex + 2);
                deleteAction.revert();
                ProjectManager.getCurrentProject().updateBlockStates();
            }
        };
    }

    public UndoManager.RevertibleAction remove(StatementBlock block) {
        return new UndoManager.RevertibleAction() {
            private int index;
            @Override
            public void execute() {
                index = getChildren().indexOf(block);
                getChildren().remove(index, index + 2);
                ProjectManager.getCurrentProject().updateBlockStates();
            }
            @Override
            public void revert() {
                add(index, block).execute();
                ProjectManager.getCurrentProject().updateBlockStates();
            }
        };
    }

    @Override
    public Iterator<StatementBlock> iterator() {
        return new Iterator<>() {
            private int i = 1;
            @Override
            public boolean hasNext() {
                return i < StatementHolder.this.getChildren().size();
            }
            @Override
            public StatementBlock next() {
                StatementBlock block = (StatementBlock) getChildren().get(i);
                i += 2;
                return block;
            }
        };
    }
}
