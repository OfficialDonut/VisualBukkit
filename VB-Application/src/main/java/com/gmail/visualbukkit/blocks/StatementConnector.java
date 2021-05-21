package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.SoundManager;
import com.gmail.visualbukkit.ui.StyleableVBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.TransferMode;
import javafx.scene.robot.Robot;
import javafx.util.Duration;

import java.util.function.Consumer;

public class StatementConnector extends StyleableVBox {

    private static StatementConnector currentConnector;
    private boolean acceptingConnections = true;

    static {
        Robot robot = new Robot();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            if (currentConnector != null && !currentConnector.localToScreen(currentConnector.getBoundsInLocal()).contains(robot.getMousePosition())) {
                currentConnector.hide();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public StatementConnector(Consumer<Statement.Block> connectAction) {
        getStyleClass().add("statement-connector");
        setPrefHeight(0);

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (acceptingConnections && (source instanceof StatementLabel || source instanceof Statement.Block)) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            connectAction.accept(source instanceof StatementLabel ? ((StatementLabel) source).createBlock() : (Statement.Block) source);
            hide();
            e.setDropCompleted(true);
            e.consume();
            SoundManager.SNAP.play();
        });
    }

    public void show() {
        if (currentConnector != this && acceptingConnections) {
            if (currentConnector != null) {
                currentConnector.hide();
            }
            setPrefHeight(25);
            currentConnector = this;
        }
    }

    public void hide() {
        currentConnector = null;
        setPrefHeight(0);
    }

    protected void setAcceptingConnections(boolean acceptingConnections) {
        this.acceptingConnections = acceptingConnections;
    }

    public boolean isAcceptingConnections() {
        return acceptingConnections;
    }
}
