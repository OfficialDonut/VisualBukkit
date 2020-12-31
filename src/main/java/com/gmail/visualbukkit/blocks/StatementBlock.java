package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import com.gmail.visualbukkit.gui.*;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.gmail.visualbukkit.util.DataFile;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@BlockColor("#90ee90ff")
public abstract class StatementBlock extends VBox implements CodeBlock, ElementInspector.Inspectable {

    protected List<BlockParameter> parameters = new ArrayList<>();
    protected ContextMenu contextMenu = new ContextMenu();
    protected VBox syntaxBox = new VBox(5);
    protected Pane nextStatementPane = new VBox();
    protected Pane nextStatementIndicator = new Pane();
    protected StatementBlock previous;
    protected StatementBlock next;
    protected boolean acceptingConnections = true;

    protected Background normalBackground = new Background(new BackgroundFill(getDefinition().getBlockColor(), CornerRadii.EMPTY, Insets.EMPTY));
    protected Border normalBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN));
    protected Background invalidatedBackground = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
    protected Border highlightedBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)), new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));

    protected Tooltip invalidatedTooltip = new Tooltip();
    private boolean valid = true;

    static {
        try {
            Tooltip tooltip = new Tooltip();
            Class<?> clazz = Class.forName("javafx.scene.control.Tooltip$TooltipBehavior");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Duration.class, Duration.class, Duration.class, boolean.class);
            constructor.setAccessible(true);
            Object tooltipBehavior = constructor.newInstance(new Duration(150), new Duration(60000), new Duration(0), false);
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            fieldBehavior.set(tooltip, tooltipBehavior);
        } catch (Exception e) {
            NotificationManager.displayException("Failed to modify tooltips", e);
        }
    }

    public StatementBlock() {
        syntaxBox.setPadding(new Insets(3));
        syntaxBox.setBorder(normalBorder);
        syntaxBox.setBackground(normalBackground);
        syntaxBox.setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                setOpacity(0.5);
                setAcceptingConnections(false);
                e.consume();
            }
        });

        nextStatementPane.getChildren().addListener((ListChangeListener<Node>) listener -> setPadding(new Insets(0, 0, listener.getList().isEmpty() ? 15 : 0, 0)));
        nextStatementIndicator.setStyle("-fx-background-color: yellow; -fx-opacity: 0.5;");
        nextStatementIndicator.setOnDragExited(e -> nextStatementIndicator.setPrefHeight(0));

        nextStatementIndicator.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if ((source instanceof StatementLabel && !StructureBlock.class.isAssignableFrom(((StatementLabel) source).getStatement().getBlockClass()))
                    || (source instanceof StatementBlock && !(source instanceof StructureBlock))) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        nextStatementIndicator.setOnDragDropped(e -> {
            UndoManager.capture();
            Object source = e.getGestureSource();
            StatementBlock block = source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (StatementBlock) source;
            connectNext(block);
            block.update();
            e.setDropCompleted(true);
            e.consume();
        });

        setPadding(new Insets(0, 0, 15, 0));
        setOnMouseDragged(Event::consume);
        setOnDragExited(e -> nextStatementIndicator.setPrefHeight(0));
        setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));

        setOnDragOver(e -> {
            if (acceptingConnections) {
                double y = e.getY() - syntaxBox.getBoundsInParent().getMaxY();
                nextStatementIndicator.setPrefHeight(y > 0 && y < 15 ? 15 : 0);
            }
        });

        setOnDragDone(e -> {
            setOpacity(1);
            setAcceptingConnections(true);
        });

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().inspect(this);
                ContextMenuManager.hide();
                e.consume();
            }
        });

        getChildren().addAll(syntaxBox, nextStatementIndicator, nextStatementPane);

        MenuItem copyItem = new MenuItem("Copy");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem exportItem = new MenuItem("Export");
        copyItem.setOnAction(e -> copy());
        cutItem.setOnAction(e -> cut());
        deleteItem.setOnAction(e -> delete());
        exportItem.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File outputDir = directoryChooser.showDialog(VisualBukkit.getInstance().getPrimaryStage());
            if (outputDir != null) {
                DataFile dataFile = new DataFile(new File(outputDir, UUID.randomUUID() + ".json").toPath());
                try {
                    dataFile.setJson(serialize());
                    dataFile.getJson().put("=", BlockRegistry.getIdentifier(this));
                    dataFile.save();
                    NotificationManager.displayMessage("Exported block", "Successfully exported block\n(" + outputDir + ")");
                } catch (IOException ex) {
                    NotificationManager.displayException("Failed to export block", ex);
                }
            }
        });

        MenuItem copyStackItem = new MenuItem("Copy Stack");
        MenuItem cutStackItem = new MenuItem("Cut Stack");
        MenuItem deleteStackItem = new MenuItem("Delete Stack");
        copyStackItem.setOnAction(e -> CopyPasteManager.copyStack(this));
        cutStackItem.setOnAction(e -> {
            UndoManager.capture();
            CopyPasteManager.copyStack(this);
            disconnect();
        });
        deleteStackItem.setOnAction(e -> {
            UndoManager.capture();
            disconnect();
        });

        contextMenu.getItems().addAll(
                copyItem, cutItem, deleteItem, new SeparatorMenuItem(),
                copyStackItem, cutStackItem, deleteStackItem, new SeparatorMenuItem(), exportItem);
        syntaxBox.setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));
    }

    protected final HBox init(Object... components) {
        HBox line = new CenteredHBox();
        init(line, components);
        return line;
    }

    protected final HBox initLine(Object... components) {
        HBox line = new CenteredHBox(new Text("  "));
        init(line, components);
        return line;
    }

    private void init(HBox hBox, Object... components) {
        syntaxBox.getChildren().add(hBox);
        for (Object component : components) {
            if (component instanceof String) {
                hBox.getChildren().add(new Text((String) component));
            } else if (component instanceof Class) {
                ExpressionParameter exprParameter = new ExpressionParameter((Class<?>) component);
                hBox.getChildren().add(exprParameter);
                component = exprParameter;
            } else if (component instanceof Node) {
                hBox.getChildren().add((Node) component);
            }
            if (component instanceof BlockParameter) {
                parameters.add((BlockParameter) component);
            }
        }
    }

    @Override
    public void update() {
        if (!valid) {
            setValid();
        }
        CodeBlock.super.update();
        if (hasNext()) {
            next.update();
        }
    }

    protected void setValid() {
        valid = true;
        syntaxBox.setBackground(normalBackground);
        Tooltip.uninstall(syntaxBox, invalidatedTooltip);
    }

    protected void setInvalid(String message) {
        valid = false;
        syntaxBox.setBackground(invalidatedBackground);
        invalidatedTooltip.setText(message);
        Tooltip.install(syntaxBox, invalidatedTooltip);
    }

    @SuppressWarnings("rawtypes")
    protected final void validateStructure(String message, Class... structureTypes) {
        StructureBlock structure = getStructure();
        if (structure != null) {
            Class<?> structureClass = structure.getClass();
            for (Class<?> structureType : structureTypes) {
                if (structureType.isAssignableFrom(structureClass)) {
                    return;
                }
            }
            setInvalid(message);
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateEvent(String message, Class... eventTypes) {
        StructureBlock structure = getStructure();
        if (structure != null) {
            if (structure instanceof EventBlock) {
                Class<?> event = ((EventBlock) structure).getEvent();
                for (Class<?> eventType : eventTypes) {
                    if (eventType.isAssignableFrom(event)) {
                        return;
                    }
                }
            }
            setInvalid(message);
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateParent(String message, Class... parentTypes) {
        if (getStructure() != null) {
            StatementBlock block = this;
            while (block != null) {
                if (block instanceof ParentBlock.ChildConnector) {
                    ParentBlock parentBlock = ((ParentBlock.ChildConnector) block).getParentStatement();
                    for (Class<?> parentType : parentTypes) {
                        if (parentType.isAssignableFrom(parentBlock.getClass())) {
                            return;
                        }
                    }
                    block = parentBlock;
                } else {
                    block = block.previous;
                }
            }
            setInvalid(message);
        }
    }

    public void connectNext(StatementBlock block) {
        block.disconnect();
        if (hasNext()) {
            block.getLast().connectNext(next);
        }
        nextStatementPane.getChildren().add(block);
        block.relocate(0, 0);
        next = block;
        block.previous = this;
    }

    public void disconnect() {
        if (hasPrevious()) {
            previous.nextStatementPane.getChildren().clear();
            previous.next = null;
            previous = null;
        } else if (getParent() != null) {
            if (getParent() instanceof Pane) {
                ((Pane) getParent()).getChildren().remove(this);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public void delete() {
        UndoManager.capture();
        Parent currentParent = getParent();
        StatementBlock currentPrevious = previous;
        disconnect();
        if (currentPrevious != null) {
            if (next != null) {
                currentPrevious.connectNext(next);
            }
        } else if (next != null && currentParent instanceof Pane) {
            StatementBlock next = this.next;
            next.disconnect();
            ((Pane) currentParent).getChildren().add(next);
            next.relocate(getLayoutX(), getLayoutY());
        }
    }

    @Override
    public void highlight() {
        syntaxBox.setBorder(highlightedBorder);
    }

    @Override
    public void unhighlight() {
        syntaxBox.setBorder(normalBorder);
    }

    protected void setAcceptingConnections(boolean state) {
        acceptingConnections = state;
        if (hasNext()) {
            next.setAcceptingConnections(state);
        }
    }

    public boolean isValid() {
        return valid;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public boolean hasNext() {
        return next != null;
    }

    public StatementBlock getPrevious() {
        return previous;
    }

    public StatementBlock getNext() {
        return next;
    }

    public StatementBlock getLast() {
        return hasNext() ? next.getLast() : this;
    }

    public StructureBlock getStructure() {
        return hasPrevious() ? previous.getStructure() : null;
    }

    @Override
    public List<BlockParameter> getParameters() {
        return parameters;
    }

    @Override
    public StatementDefinition<?> getDefinition() {
        return BlockRegistry.getStatement(this);
    }
}
