package us.donut.visualbukkit.blocks;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.editor.ContextMenuManager;
import us.donut.visualbukkit.util.DataConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentBlock extends StatementBlock {

    private static Color[] colors = {Color.LIGHTBLUE, Color.CORNFLOWERBLUE, Color.STEELBLUE};
    protected VBox container = new VBox();
    protected Pane childIndicator = new Pane();
    protected ChildConnector childConnector = new ChildConnector();

    public ParentBlock() {
        syntaxBox.getStyleClass().clear();
        syntaxBox.setSpacing(5);
        syntaxBox.setPadding(new Insets(0, 0, 1, 0));

        container.getStyleClass().add("parent-block");
        container.getChildren().addAll(syntaxBox, childIndicator, childConnector);
        setupChildConnector();
        updateColor();
        getChildren().add(0, container);

        container.setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));
        container.setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                setOpacity(0.5);
                DRAG_CACHE.clear();
                e.consume();
            }
        });

        setOnDragOver(e -> {
            double y = e.getY() - container.getBoundsInParent().getMaxY();
            nextStatementIndicator.setPrefHeight(y > 0 && y < 15 ? 15 : 0);
        });
    }

    private void setupChildConnector() {
        childIndicator.setPrefHeight(15);
        childIndicator.setOpacity(0.5);
        childIndicator.setOnDragExited(e -> childIndicator.setStyle(null));
        childIndicator.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementLabel || source instanceof StatementBlock) {
                childIndicator.setStyle("-fx-background-color: yellow;");
                Boolean isValid = StatementBlock.DRAG_CACHE.get(childIndicator);
                if (isValid == null) {
                    isValid = false;
                    StatementBlock first = source instanceof StatementLabel ?
                            ((StatementLabel) source).getValidationBlock() :
                            (StatementBlock) source;
                    if (!equals(source) && !source.equals(getChild()) && !isChild(childIndicator, (Parent) source)) {
                        StatementBlock last = first.getLast();
                        StatementBlock currentChild = getChild();
                        StatementBlock currentPrevious = first.previous;
                        childConnector.next = first;
                        first.previous = childConnector;
                        if (currentChild != null) {
                            last.next = currentChild;
                            currentChild.previous = last;
                        }
                        if (currentPrevious != null) {
                            currentPrevious.next = null;
                        }
                        try {
                            first.validate();
                            isValid = true;
                        } catch (IllegalStateException ignored) {}
                        childConnector.next = currentChild;
                        first.previous = currentPrevious;
                        if (currentChild != null) {
                            last.next = null;
                            currentChild.previous = childConnector;
                        }
                        if (currentPrevious != null) {
                            currentPrevious.next = first;
                        }
                    }
                    StatementBlock.DRAG_CACHE.put(childIndicator, isValid);
                }
                if (isValid) {
                    e.acceptTransferModes(TransferMode.ANY);
                }
            }
            e.consume();
        });

        childIndicator.setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            connectChild(source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (StatementBlock) source);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        if (hasChild()) {
            getChild().validate();
        }
    }

    @Override
    public void update() {
        super.update();
        updateColor();
        if (hasChild()) {
            getChild().update();
        }
    }

    public void connectChild(StatementBlock block) {
        block.disconnect();
        if (hasChild()) {
            block.getLast().connectNext(getChild());
        }
        childConnector.connectNext(block);
        if (block.equals(this)) {
            System.out.println("!!!!");
        }
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

    @Override
    public void saveTo(DataConfig config) {
        super.saveTo(config);
        List<DataConfig> childConfigs = new ArrayList<>();
        StatementBlock child = getChild();
        while (child != null) {
            DataConfig childConfig = new DataConfig();
            childConfig.set("=", child.getIdentifier());
            child.saveTo(childConfig);
            childConfigs.add(childConfig);
            child = child.getNext();
        }
        config.set("children", childConfigs);
    }

    @Override
    public void loadFrom(DataConfig config) {
        super.loadFrom(config);
        List<DataConfig> childConfigs = config.getConfigList("children");
        for (DataConfig childConfig : childConfigs) {
            StatementDefinition<?> statement = BlockRegistry.getStatement(childConfig.getString("="));
            if (statement != null) {
                StatementBlock child = statement.createBlock(childConfig);
                if (hasChild()) {
                    getChild().getLast().connectNext(child);
                } else {
                    connectChild(child);
                }
            }
        }
    }

    private void updateColor() {
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
        Color color = colors[level % colors.length];
        container.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        StatementBlock child = getChild();
        while (child != null) {
            if (child instanceof ParentBlock) {
                ((ParentBlock) child).updateColor();
            }
            child = child.getNext();
        }
    }

    public class ChildConnector extends StatementBlock {

        public ChildConnector() {
            getChildren().clear();
            getChildren().add(nextStatementPane);
        }

        @Override
        protected Syntax init() {
            return null;
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
    }
}
