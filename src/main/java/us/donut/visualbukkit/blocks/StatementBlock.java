package us.donut.visualbukkit.blocks;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.structures.StructEventListener;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.editor.*;
import us.donut.visualbukkit.util.CenteredHBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StatementBlock extends VBox implements CodeBlock {

    public static final Map<Object, Boolean> DRAG_CACHE = new HashMap<>();
    protected List<BlockParameter> parameters = new ArrayList<>();
    protected ContextMenu contextMenu = new ContextMenu();
    protected VBox syntaxBox = new VBox();
    protected Pane nextStatementPane = new VBox();
    protected Pane nextStatementIndicator = new Pane();
    protected StatementBlock previous;
    protected StatementBlock next;

    public StatementBlock() {
        setupSyntax();
        setupNextStatementConnector();
        setFillWidth(false);
        setPadding(new Insets(0, 0, 15, 0));
        setOnMouseDragged(Event::consume);
        getChildren().addAll(syntaxBox, nextStatementIndicator, nextStatementPane);
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem deleteItem = new MenuItem("Delete");
        copyItem.setOnAction(e -> CopyPasteManager.copy(this));
        deleteItem.setOnAction(e -> {
            if (canDelete()) {
                UndoManager.capture();
                StatementBlock currentPrevious = previous;
                StatementBlock currentNext = next;
                disconnect();
                if (currentPrevious != null) {
                    if (currentNext != null) {
                        currentPrevious.connectNext(next);
                    }
                } else if (currentNext != null) {
                    currentNext.disconnect();
                    ProjectManager.getCurrentProject().getCurrentCanvas().add(currentNext, contextMenu.getX(), contextMenu.getY());
                }
            } else {
                VisualBukkit.displayError("Invalid deletion", "Cannot delete this block");
            }
        });
        contextMenu.getItems().addAll(copyItem, deleteItem);
        syntaxBox.setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));
    }

    protected abstract Syntax init();

    private void setupSyntax() {
        syntaxBox.getStyleClass().add("statement-block");
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
                DRAG_CACHE.clear();
                e.consume();
            }
        });
        Syntax syntax = init();
        if (syntax != null) {
            CenteredHBox syntaxLine = new CenteredHBox(5);
            syntaxLine.setFillHeight(false);
            syntaxBox.getChildren().add(syntaxLine);
            for (Object component : syntax.getComponents()) {
                if (component instanceof BlockParameter) {
                    parameters.add((BlockParameter) component);
                }
                if (component instanceof Node) {
                    syntaxLine.getChildren().add((Node) component);
                } else if (component == Syntax.LINE_SEPARATOR) {
                    syntaxLine = new CenteredHBox(5);
                    syntaxLine.setFillHeight(false);
                    syntaxLine.setPadding(new Insets(0, 0, 0, 15));
                    syntaxBox.getChildren().add(syntaxLine);
                }
            }
        }
    }

    private void setupNextStatementConnector() {
        nextStatementPane.getChildren().addListener((ListChangeListener<Node>) listener ->
                setPadding(new Insets(0, 0, listener.getList().isEmpty() ? 15 : 0, 0)));
        nextStatementIndicator.setStyle("-fx-background-color: yellow; -fx-opacity: 0.5;");
        nextStatementIndicator.prefWidthProperty().bind(widthProperty());
        nextStatementIndicator.setOnDragExited(e -> nextStatementIndicator.setPrefHeight(0));
        nextStatementIndicator.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementLabel || source instanceof StatementBlock) {
                Boolean isValid = StatementBlock.DRAG_CACHE.get(nextStatementIndicator);
                if (isValid == null) {
                    isValid = false;
                    StatementBlock first = source instanceof StatementLabel ?
                            ((StatementLabel) source).getValidationBlock() :
                            (StatementBlock) source;
                    if (!equals(first.previous) && !isChild(nextStatementIndicator, (Parent) source)) {
                        StatementBlock last = first.getLast();
                        StatementBlock currentNext = next;
                        StatementBlock currentPrevious = first.previous;
                        next = first;
                        first.previous = this;
                        if (currentNext != null) {
                            last.next = currentNext;
                            currentNext.previous = last;
                        }
                        if (currentPrevious != null) {
                            currentPrevious.next = null;
                        }
                        try {
                            first.validate();
                            isValid = true;
                        } catch (IllegalStateException ignored) {}
                        next = currentNext;
                        first.previous = currentPrevious;
                        if (currentNext != null) {
                            last.next = null;
                            currentNext.previous = this;
                        }
                        if (currentPrevious != null) {
                            currentPrevious.next = first;
                        }
                    }
                    StatementBlock.DRAG_CACHE.put(nextStatementIndicator, isValid);
                }
                if (isValid) {
                    e.acceptTransferModes(TransferMode.ANY);
                }
            }
            e.consume();
        });

        nextStatementIndicator.setOnDragDropped(e -> {
            UndoManager.capture();
            Object source = e.getGestureSource();
            connectNext(source instanceof StatementLabel ?
                    ((StatementLabel) source).getStatement().createBlock() :
                    (StatementBlock) source);
            e.setDropCompleted(true);
            e.consume();
        });

        setOnDragExited(e -> nextStatementIndicator.setPrefHeight(0));
        setOnDragDone(e -> setOpacity(1));
        setOnDragOver(e -> {
            double y = e.getY() - syntaxBox.getBoundsInParent().getMaxY();
            nextStatementIndicator.setPrefHeight(y > 0 && y < 15 ? 15 : 0);
        });
    }

    protected boolean isChild(Node node, Parent parent) {
        while (node != null && !(node instanceof BlockCanvas)) {
            if (node.getParent().equals(parent)) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    public boolean canDelete() {
        if (hasPrevious()) {
            previous.next = next;
        }
        if (hasNext()) {
            next.previous = previous;
        }
        boolean canDelete = false;
        try {
            if (hasPrevious()) {
                previous.validate();
            } else if (hasNext()) {
                next.validate();
            }
            canDelete = true;
        } catch (Exception ignored) {}
        if (hasPrevious()) {
            previous.next = this;
        }
        if (hasNext()) {
            next.previous = this;
        }
        return canDelete;
    }

    public void validate() throws IllegalStateException {
        if (hasNext()) {
            next.validate();
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateStructure(Class... structureTypes) throws IllegalStateException {
        StructureBlock structure = getStructure();
        if (structure != null) {
            Class<?> structureClass = structure.getClass();
            for (Class<?> structureType : structureTypes) {
                if (structureType.isAssignableFrom(structureClass)) {
                    return;
                }
            }
            throw new IllegalStateException();
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateEvent(Class... eventTypes) throws IllegalStateException {
        StructureBlock structure = getStructure();
        if (structure != null) {
            if (structure instanceof StructEventListener) {
                Class<?> event = ((StructEventListener) structure).getEvent();
                for (Class<?> eventType : eventTypes) {
                    if (eventType.isAssignableFrom(event)) {
                        return;
                    }
                }
            }
            throw new IllegalStateException();
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateParent(Class... parentTypes) throws IllegalStateException {
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
            throw new IllegalStateException();
        }
    }

    @Override
    public void update() {
        CodeBlock.super.update();
        if (hasNext()) {
            next.update();
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
        block.update();
    }

    public void disconnect() {
        if (hasPrevious()) {
            previous.nextStatementPane.getChildren().clear();
            previous.next = null;
            previous = null;
        } else if (getParent() != null) {
            Parent parent = getParent().getParent();
            if (parent instanceof BlockCanvas) {
                ((BlockCanvas) parent).remove(this);
            }
        }
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

    public StructureBlock getStructure() {
        return hasPrevious() ? previous.getStructure() : null;
    }

    public StatementBlock getLast() {
        return hasNext() ? next.getLast() : this;
    }

    public HBox getSyntaxLine(int i) {
        return (HBox) syntaxBox.getChildren().get(i);
    }

    @Override
    public List<BlockParameter> getParameters() {
        return parameters;
    }
}
