package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.gui.UndoManager;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.geom.Point2D;

@BlockColor("#add8e6ff")
public abstract class ParentBlock extends StatementBlock {

    private static Color[] colors = {Color.LIGHTBLUE, Color.CORNFLOWERBLUE, Color.STEELBLUE};

    private VBox container = new VBox();
    private Pane childIndicator = new Pane();
    private ChildConnector childConnector = new ChildConnector();
    private Background[] backgrounds = new Background[colors.length];

    public ParentBlock() {
        syntaxBox.setBorder(null);
        syntaxBox.setBackground(null);
        syntaxBox.setPadding(new Insets(0, 3, 1, -10));

        container.setPadding(new Insets(3, -1, 3, 15));
        container.setBorder(normalBorder);
        container.getChildren().addAll(syntaxBox, childIndicator, childConnector);
        container.setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null), e.getX(), e.getY());
                ClipboardContent content = new ClipboardContent();
                content.put(BlockCanvas.POINT, new Point2D.Double(e.getX(), e.getY()));
                dragboard.setContent(content);
                setOpacity(0.5);
                setAcceptingConnections(false);
                e.consume();
            }
        });

        childIndicator.setPrefHeight(15);
        childIndicator.setOpacity(0.5);
        childIndicator.setOnDragExited(e -> childIndicator.setStyle(null));

        childIndicator.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (acceptingConnections) {
                if ((source instanceof StatementLabel && !StructureBlock.class.isAssignableFrom(((StatementLabel) source).getStatement().getBlockClass()))
                        || (source instanceof StatementBlock && !(source instanceof StructureBlock))) {
                    childIndicator.setStyle("-fx-background-color: yellow;");
                    e.acceptTransferModes(TransferMode.ANY);
                }
            }
            e.consume();
        });

        childIndicator.setOnDragDropped(e -> {
            UndoManager.capture();
            Object source = e.getGestureSource();
            StatementBlock block = source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (StatementBlock) source;
            connectChild(block);
            block.update();
            e.setDropCompleted(true);
            e.consume();
        });

        setOnDragOver(e -> {
            if (acceptingConnections) {
                double y = e.getY() - container.getBoundsInParent().getMaxY();
                nextStatementIndicator.setPrefHeight(y > 0 && y < 15 ? 15 : 0);
            }
        });

        getChildren().add(0, container);
        color();

        for (int i = 0; i < colors.length; i++) {
            backgrounds[i] = new Background(new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY));
        }
    }

    @Override
    public void update() {
        super.update();
        if (hasChild()) {
            getChild().update();
        }
        color();
    }

    @Override
    protected void setValid() {
        super.setValid();
        Tooltip.uninstall(container, invalidatedTooltip);
        syntaxBox.setBackground(null);
        color();
    }

    @Override
    protected void setInvalid(String message) {
        super.setInvalid(message);
        Tooltip.install(container, invalidatedTooltip);
        syntaxBox.setBackground(null);
        color();
    }

    private void color() {
        if (isValid()) {
            int level = 0;
            StatementBlock block = this;
            while (block != null) {
                if (block instanceof ChildConnector) {
                    level++;
                    block = ((ChildConnector) block).getParentStatement();
                } else {
                    block = block.previous;
                }
            }
            container.setBackground(backgrounds[level % backgrounds.length]);
        } else {
            container.setBackground(invalidatedBackground);
        }
    }

    @Override
    public void highlight() {
        container.setBorder(highlightedBorder);
    }

    @Override
    public void unhighlight() {
        container.setBorder(normalBorder);
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = super.serialize();
        JSONArray childArray = new JSONArray();
        StatementBlock child = getChild();
        while (child != null) {
            JSONObject childObj = child.serialize();
            childObj.put("=", BlockRegistry.getIdentifier(child));
            childArray.put(childObj);
            child = child.getNext();
        }
        obj.put("children", childArray);
        return obj;
    }

    @Override
    public void deserialize(JSONObject obj) {
        super.deserialize(obj);
        JSONArray childArray = obj.optJSONArray("children");
        if (childArray != null) {
            for (Object o : childArray) {
                if (o instanceof JSONObject) {
                    JSONObject childObj = (JSONObject) o;
                    StatementDefinition<?> statement = BlockRegistry.getStatement(childObj.optString("="));
                    if (statement != null) {
                        StatementBlock child = statement.createBlock(childObj);
                        if (hasChild()) {
                            getChild().getLast().connectNext(child);
                        } else {
                            connectChild(child);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void setAcceptingConnections(boolean state) {
        super.setAcceptingConnections(state);
        if (hasChild()) {
            getChild().setAcceptingConnections(state);
        }
    }

    public void connectChild(StatementBlock block) {
        block.disconnect();
        if (hasChild()) {
            block.getLast().connectNext(getChild());
        }
        childConnector.connectNext(block);
    }

    public boolean hasChild() {
        return childConnector.hasNext();
    }

    public StatementBlock getChild() {
        return childConnector.getNext();
    }

    public String getChildJava() {
        StringBuilder builder = new StringBuilder();
        StatementBlock child = getChild();
        while (child != null) {
            builder.append(child.toJava());
            child = child.getNext();
        }
        return builder.toString();
    }

    protected class ChildConnector extends StatementBlock {

        public ChildConnector() {
            getChildren().clear();
            getChildren().add(nextStatementPane);
            setOnMouseClicked(e -> {});
            setOnContextMenuRequested(e -> {});
        }

        @Override
        public String toJava() {
            throw new UnsupportedOperationException();
        }

        public ParentBlock getParentStatement() {
            return ParentBlock.this;
        }

        @Override
        public StructureBlock getStructure() {
            return ParentBlock.this.getStructure();
        }

        @Override
        public StatementDefinition<?> getDefinition() {
            try {
                return new StatementDefinition<>(ParentBlock.this.getClass());
            } catch (NoSuchMethodException e) {
                NotificationManager.displayException("Failed to create child connector definition", e);
                return null;
            }
        }
    }
}
