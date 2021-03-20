package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.gui.SoundManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.util.Duration;

public class StatementConnector extends VBox {

    private static StatementConnector currentConnector;

    private CodeBlock<?> owner;
    private Statement.Block connected;
    private VBox placementIndicator = new VBox();
    private VBox statementHolder = new VBox();
    private boolean acceptingConnections = true;

    static {
        Robot robot = new Robot();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            if (currentConnector != null && !currentConnector.placementIndicator.localToScreen(currentConnector.placementIndicator.getBoundsInLocal()).contains(robot.getMousePosition())) {
                currentConnector.hideIndicator();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public StatementConnector(CodeBlock<?> owner) {
        this.owner = owner;

        setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(placementIndicator, statementHolder);

        placementIndicator.getStyleClass().add("statement-connector");
        placementIndicator.setPrefHeight(0);

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
            hideIndicator();
            e.setDropCompleted(true);
            e.consume();
            SoundManager.SNAP.play();
        });
    }

    public void showIndicator() {
        if (currentConnector != this && isAcceptingConnections()) {
            if (currentConnector != null) {
                currentConnector.hideIndicator();
            }
            placementIndicator.setPrefHeight(25);
            currentConnector = this;
        }
    }

    public void hideIndicator() {
        currentConnector = null;
        placementIndicator.setPrefHeight(0);
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
