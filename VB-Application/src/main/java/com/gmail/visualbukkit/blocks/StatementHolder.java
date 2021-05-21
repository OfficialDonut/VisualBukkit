package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.StyleableVBox;
import com.gmail.visualbukkit.ui.UndoManager;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatementHolder extends StyleableVBox {

    public UndoManager.RevertableAction addFirst(Statement.Block... blocks) {
        return add(null, blocks);
    }

    public UndoManager.RevertableAction addLast(Statement.Block... blocks) {
        return add(getChildren().isEmpty() ? null : (Statement.Block) getChildren().get(getChildren().size() - 1), blocks);
    }

    public UndoManager.RevertableAction add(Statement.Block prev, Statement.Block... blocks) {
        if (blocks.length == 1) {
            StatementHolder statementHolder = blocks[0].getStatementHolder();
            if (statementHolder != null) {
                return addStack(prev, blocks[0], statementHolder);
            } else {
                return addSingle(prev, blocks[0]);
            }
        } else {
            return addMultiple(prev, blocks);
        }
    }

    private UndoManager.RevertableAction addSingle(Statement.Block prev, Statement.Block block) {
        return new UndoManager.RevertableAction() {
            @Override
            public void run() {
                getChildren().add(prev != null ? getChildren().indexOf(prev) + 1 : 0, block);
                updateBlocks();
            }
            @Override
            public void revert() {
                getChildren().remove(block);
                updateBlocks();
            }
        };
    }

    private UndoManager.RevertableAction addMultiple(Statement.Block prev, Statement.Block... blocks) {
        return new UndoManager.RevertableAction() {
            @Override
            public void run() {
                getChildren().addAll(prev != null ? getChildren().indexOf(prev) + 1 : 0, Arrays.asList(blocks));
                updateBlocks();
            }
            @Override
            public void revert() {
                getChildren().removeAll(blocks);
                updateBlocks();
            }
        };
    }

    private UndoManager.RevertableAction addStack(Statement.Block prev, Statement.Block block, StatementHolder statementHolder) {
        return new UndoManager.RevertableAction() {
            int oldIndex;
            List<Node> stack;
            @Override
            public void run() {
                oldIndex = statementHolder.getChildren().indexOf(block);
                List<Node> subList = statementHolder.getChildren().subList(oldIndex, statementHolder.getChildren().size());
                stack = new ArrayList<>(subList);
                subList.clear();
                getChildren().addAll(prev != null ? getChildren().indexOf(prev) + 1 : 0, stack);
                updateBlocks();
                if (statementHolder != StatementHolder.this) {
                    statementHolder.updateBlocks();
                }
            }
            @Override
            public void revert() {
                getChildren().removeAll(stack);
                statementHolder.getChildren().addAll(oldIndex, stack);
                updateBlocks();
                if (statementHolder != StatementHolder.this) {
                    statementHolder.updateBlocks();
                }
            }
        };
    }

    public UndoManager.RevertableAction remove(Statement.Block block) {
        return new UndoManager.RevertableAction() {
            int oldIndex;
            @Override
            public void run() {
                getChildren().remove(oldIndex = getChildren().indexOf(block));
                updateBlocks();
            }
            @Override
            public void revert() {
                getChildren().add(oldIndex, block);
                updateBlocks();
            }
        };
    }

    public UndoManager.RevertableAction removeStack(Statement.Block block) {
        return new UndoManager.RevertableAction() {
            int oldIndex;
            List<Node> stack;
            @Override
            public void run() {
                oldIndex = getChildren().indexOf(block);
                List<Node> subList = getChildren().subList(oldIndex, getChildren().size());
                stack = new ArrayList<>(subList);
                subList.clear();
                updateBlocks();
            }
            @Override
            public void revert() {
                getChildren().addAll(oldIndex, stack);
                updateBlocks();
            }
        };
    }

    public UndoManager.RevertableAction replace(Statement.Block blockToReplace, Statement.Block block) {
        return new UndoManager.RevertableAction() {
            @Override
            public void run() {
                getChildren().set(getChildren().indexOf(blockToReplace), block);
                updateBlocks();
            }
            @Override
            public void revert() {
                getChildren().set(getChildren().indexOf(block), blockToReplace);
                updateBlocks();
            }
        };
    }

    private void updateBlocks() {
        for (Node node : getChildren()) {
            ((Statement.Block) node).update();
        }
    }

    public Statement.Block getPrevious(Statement.Block block) {
        int i = getChildren().indexOf(block) - 1;
        return i >= 0 ? (Statement.Block) getChildren().get(i) : null;
    }

    public Statement.Block getNext(Statement.Block block) {
        int i = getChildren().indexOf(block) + 1;
        return i < getChildren().size() ? (Statement.Block) getChildren().get(i) : null;
    }

    public Statement.Block getFirst() {
        return getChildren().isEmpty() ? null : (Statement.Block) getChildren().get(0);
    }

    public Statement.Block getLast() {
        int i = getChildren().size() - 1;
        return i >= 0 ? (Statement.Block) getChildren().get(i) : null;
    }

    @SuppressWarnings("unchecked")
    public List<Statement.Block> getBlocks() {
        return Collections.unmodifiableList((ObservableList<Statement.Block>) (Object) getChildren());
    }
}
