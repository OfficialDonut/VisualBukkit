package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.definitions.StatComment;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.ui.StyleableVBox;
import com.gmail.visualbukkit.ui.UndoManager;
import javafx.scene.Node;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatementHolder extends StyleableVBox {

    private BlockNode owner;
    private StatementConnector ownerConnector;
    private boolean debugMode;

    public StatementHolder(BlockNode owner, StatementConnector ownerConnector) {
        this.owner = owner;
        this.ownerConnector = ownerConnector;
    }

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
        for (Statement.Block block : getBlocks()) {
            block.update();
        }
    }

    public StatementConnector getPreviousConnector(Statement.Block block) {
        Statement.Block prev = getPrevious(block);
        return prev != null ? prev.getConnector() : ownerConnector;
    }

    public Statement.Block getPrevious(Statement.Block block) {
        int i = getChildren().indexOf(block) - 1;
        return i >= 0 ? (Statement.Block) getChildren().get(i) : null;
    }

    public Statement.Block getNext(Statement.Block block) {
        int i = getChildren().indexOf(block) + 1;
        return i < getChildren().size() ? (Statement.Block) getChildren().get(i) : null;
    }

    public StatementConnector getLastEnabledConnector() {
        if (!getChildren().isEmpty()) {
            for (int i = getChildren().size() - 1; i >= 0; i--) {
                Statement.Block block = (Statement.Block) getChildren().get(i);
                if (!block.isDisabled()) {
                    return block.getConnector();
                }
            }
        }
        return ownerConnector;
    }

    public BlockNode getOwner() {
        return owner;
    }

    @SuppressWarnings("unchecked")
    public List<Statement.Block> getBlocks() {
        return (List<Statement.Block>) (Object) getChildrenUnmodifiable();
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public String toJava() {
        List<Statement.Block> blocks = getBlocks();
        if (debugMode) {
            String java = "";
            for (int i = blocks.size() - 1; i >= 0; i--) {
                Statement.Block block = blocks.get(i);
                if (block instanceof Container.Block) {
                    java = block.toJava() + java;
                } else if (block.getDefinition().getClass() != StatComment.class) {
                    String id = RandomStringUtils.randomAlphanumeric(16);
                    ProjectManager.getCurrentProject().getDebugMap().put(id, block);
                    java = new StringBuilder()
                            .append("try {")
                            .append(block.toJava())
                            .append(java)
                            .append("} catch (Exception $").append(id).append(") {")
                            .append("PluginMain.reportError(\"").append(id).append("\",$").append(id).append(");")
                            .append("}")
                            .toString();
                }
            }
            return java;
        }
        StringBuilder builder = new StringBuilder();
        for (Statement.Block block : blocks) {
            builder.append(block.toJava());
        }
        return builder.toString();
    }
}
