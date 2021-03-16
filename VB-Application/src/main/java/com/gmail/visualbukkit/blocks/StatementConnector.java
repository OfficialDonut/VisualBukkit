package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.gui.SoundManager;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StatementConnector extends StackPane {

    private static final PseudoClass HIDDEN_STYLE_CLASS = PseudoClass.getPseudoClass("hidden");

    private CodeBlock<?> owner;
    private Statement.Block connected;
    private VBox placementIndicator = new VBox();
    private VBox statementHolder = new VBox();
    private boolean acceptingConnections = true;

    public StatementConnector(CodeBlock<?> owner) {
        this.owner = owner;

        setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(statementHolder, placementIndicator);

        placementIndicator.getStyleClass().add("statement-connector");
        placementIndicator.pseudoClassStateChanged(HIDDEN_STYLE_CLASS, true);

        placementIndicator.setOnDragEntered(e -> {
            if (isAcceptingConnections()) {
                placementIndicator.pseudoClassStateChanged(HIDDEN_STYLE_CLASS, false);
                statementHolder.setPadding(new Insets(placementIndicator.getHeight(), 0, 0, 0));
            }
            e.consume();
        });

        placementIndicator.setOnDragExited(e -> {
            placementIndicator.pseudoClassStateChanged(HIDDEN_STYLE_CLASS, true);
            statementHolder.setPadding(Insets.EMPTY);
            e.consume();
        });

        placementIndicator.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (acceptingConnections && (source instanceof StatementLabel || source instanceof Statement.Block)) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        placementIndicator.setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            Statement.Block block = source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (Statement.Block) source;
            UndoManager.run(connect(block));
            e.setDropCompleted(true);
            e.consume();
            SoundManager.SNAP.play();
        });

        placementIndicator.setOnDragDetected(e -> {
            if (hasConnection()) {
                connected.getSyntaxBox().getOnDragDetected().handle(e);
            }
            e.consume();
        });

        placementIndicator.setOnContextMenuRequested(e -> {
            if (hasConnection()) {
                connected.getSyntaxBox().getOnContextMenuRequested().handle(e);
            }
            e.consume();
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
                statementHolder.getChildren().add(connected);
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
                statementHolder.getChildren().clear();
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
                statementHolder.getChildren().clear();
            }
            @Override
            public void revert() {
                connected = oldConnected;
                connected.setPrevious(StatementConnector.this);
                statementHolder.getChildren().add(connected);
            }
        };
    }

    protected void setAcceptingConnections(boolean acceptingConnections) {
        this.acceptingConnections = acceptingConnections;
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

    public VBox getPlacementIndicator() {
        return placementIndicator;
    }

    public VBox getStatementHolder() {
        return statementHolder;
    }
}
