package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.definitions.StatComment;
import com.gmail.visualbukkit.project.BuildInfo;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class StatementHolder extends VBox implements Iterable<StatementBlock> {

    private final StatementConnector initialConnector = new StatementConnector(this);
    private final Block owner;

    public StatementHolder(Block owner) {
        this.owner = owner;
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

    public void addFirst(StatementBlock... blocks) {
        for (int i = blocks.length - 1; i >= 0; i--) {
            add(1, blocks[i]);
        }
    }

    public void addLast(StatementBlock... blocks) {
        for (StatementBlock block : blocks) {
            add(getChildren().size(), block);
        }
    }

    public void addBefore(StatementBlock block, StatementBlock... blocks) {
        for (StatementBlock b : blocks) {
            add(getChildren().indexOf(block), b);
        }
    }

    public void addAfter(StatementBlock block, StatementBlock... blocks) {
        for (StatementBlock b : blocks) {
            add(getChildren().indexOf(block) + 2, b);
            block = b;
        }
    }

    protected void add(int index, StatementBlock block) {
        getChildren().add(index, block);
        getChildren().add(index + 1, new StatementConnector(this));
    }

    public void remove(StatementBlock block) {
        int index = getChildren().indexOf(block);
        getChildren().remove(index, index + 2);
    }

    public void removeStack(StatementBlock block) {
        getChildren().remove(getChildren().indexOf(block), getChildren().size());
    }

    public List<StatementBlock> getStack(StatementBlock block) {
        List<StatementBlock> blocks = new ArrayList<>();
        for (int i = getChildren().indexOf(block); i < getChildren().size(); i += 2) {
            blocks.add((StatementBlock) getChildren().get(i));
        }
        return blocks;
    }

    public StatementBlock getPrevious(StatementBlock block) {
        StatementBlock prev = null;
        for (StatementBlock b : this) {
            if (b.equals(block)) {
                return prev;
            }
            if (!(b instanceof StatComment)) {
                prev = b;
            }
        }
        return null;
    }

    public void setCollapsedRecursive(boolean collapsed) {
        for (StatementBlock block : this) {
            block.setCollapsed(collapsed);
            if (block instanceof ContainerBlock c) {
                c.getChildStatementHolder().setCollapsedRecursive(collapsed);
            }
        }
    }

    public boolean isEmpty() {
        return getChildren().size() == 1;
    }

    public String generateJava(BuildInfo buildInfo) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (StatementBlock block : this) {
            joiner.add(buildInfo.isDebugMode() ? block.generateDebugJava(buildInfo) : block.generateJava(buildInfo));
        }
        return joiner.toString();
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

    public Block getOwner() {
        return owner;
    }
}
