package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.gui.SoundManager;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class StatementConnector extends HBox {

    public static final DataFormat POINT_DATA_FORMAT = new DataFormat("geom/Point2D");
    private static StatementConnector currentlyShown;

    private CodeBlock<?> owner;
    private Pane blockHolder;
    private Statement.Block connected;
    private boolean acceptingConnections = true;

    public StatementConnector(CodeBlock<?> owner, Pane blockHolder) {
        this.owner = owner;
        this.blockHolder = blockHolder;

        getStyleClass().add("statement-connector");

        setPrefHeight(0);
        setMaxHeight(25);
        setOnMouseEntered(e -> setPrefHeight(0));
        setOnDragExited(e -> setPrefHeight(0));

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (acceptingConnections && (source instanceof StatementLabel || source instanceof Statement.Block)) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            Statement.Block block = source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (Statement.Block) source;
            UndoManager.run(connect(block));
            e.setDropCompleted(true);
            e.consume();
            SoundManager.SNAP.play();
        });
    }

    public UndoManager.RevertableAction connect(Statement.Block block) {
        if (connected == block) {
            return UndoManager.EMPTY_ACTION;
        }

        UndoManager.RevertableAction disconnectNew = block.getPrevious() != null ? block.getPrevious().disconnect() : UndoManager.EMPTY_ACTION;
        UndoManager.RevertableAction disconnectOld = disconnect();
        Statement.Block oldConnected = connected;

        return new UndoManager.RevertableAction() {
            UndoManager.RevertableAction connectOldToNew;
            @Override
            public void run() {
                disconnectNew.run();
                disconnectOld.run();
                connected = block;
                connected.setPrevious(StatementConnector.this);
                blockHolder.getChildren().add(connected);
                if (oldConnected != null) {
                    connectOldToNew = connected.getLast().getNext().connect(oldConnected);
                    connectOldToNew.run();
                } else {
                    connectOldToNew = UndoManager.EMPTY_ACTION;
                }
            }
            @Override
            public void revert() {
                connectOldToNew.revert();
                connected.setPrevious(null);
                connected = null;
                blockHolder.getChildren().clear();
                disconnectOld.revert();
                disconnectNew.revert();
            }
        };
    }

    public UndoManager.RevertableAction disconnect() {
        if (!hasConnection()) {
            return UndoManager.EMPTY_ACTION;
        }
        Statement.Block oldConnected = connected;
        return new UndoManager.RevertableAction() {
            @Override
            public void run() {
                connected.setPrevious(null);
                connected = null;
                blockHolder.getChildren().clear();
            }
            @Override
            public void revert() {
                connected = oldConnected;
                connected.setPrevious(StatementConnector.this);
                blockHolder.getChildren().add(connected);
            }
        };
    }

    protected void show() {
        if (currentlyShown != null) {
            currentlyShown.setPrefHeight(0);
        }
        currentlyShown = this;
        setPrefHeight(getMaxHeight());
    }

    protected void setAcceptingConnections(boolean acceptingConnections) {
        this.acceptingConnections = acceptingConnections;
        if (hasConnection()) {
            connected.getNext().setAcceptingConnections(acceptingConnections);
        }
    }

    public boolean isAcceptingConnections() {
        return acceptingConnections;
    }

    public boolean hasConnection() {
        return connected != null;
    }

    public Statement.Block getConnected() {
        return connected;
    }

    public CodeBlock<?> getOwner() {
        return owner;
    }
}
