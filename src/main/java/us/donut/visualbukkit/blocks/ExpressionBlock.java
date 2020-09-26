package us.donut.visualbukkit.blocks;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.editor.ContextMenuManager;
import us.donut.visualbukkit.editor.CopyPasteManager;
import us.donut.visualbukkit.editor.UndoManager;
import us.donut.visualbukkit.util.CenteredHBox;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionBlock<T> extends CenteredHBox implements CodeBlock {

    protected List<BlockParameter> parameters = new ArrayList<>();
    protected ContextMenu contextMenu = new ContextMenu();

    public ExpressionBlock() {
        getStyleClass().add("expression-block");
        setOnMouseClicked(Event::consume);

        Syntax syntax = init();
        if (syntax != null) {
            for (Object component : syntax.getComponents()) {
                if (component instanceof BlockParameter) {
                    parameters.add((BlockParameter) component);
                }
                if (component instanceof Node) {
                    getChildren().add((Node) component);
                }
            }
        }

        MenuItem copyItem = new MenuItem("Copy");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem deleteItem = new MenuItem("Delete");
        copyItem.setOnAction(e -> CopyPasteManager.copy(this));
        cutItem.setOnAction(e -> {
            CopyPasteManager.copy(this);
            UndoManager.capture();
            ((ExpressionParameter) getParent()).getComboBox().setValue(null);
        });
        deleteItem.setOnAction(e -> {
            UndoManager.capture();
            ((ExpressionParameter) getParent()).getComboBox().setValue(null);
        });
        contextMenu.getItems().addAll(copyItem, cutItem, deleteItem);
        setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));
    }

    protected abstract Syntax init();

    public String modify(ModificationType modificationType, String delta) {
        throw new UnsupportedOperationException();
    }

    public Class<?> getDeltaType(ModificationType modificationType) {
        return getReturnType();
    }

    @Override
    public void update() {
        CodeBlock.super.update();
    }

    @SuppressWarnings("rawtypes")
    protected final void validateStructure(Class... structureTypes) {
        try {
            getStatement().validateStructure(structureTypes);
            validate();
        } catch (IllegalStateException e) {
            invalidate();
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateEvent(Class... eventTypes) {
        try {
            getStatement().validateEvent(eventTypes);
            validate();
        } catch (IllegalStateException e) {
            invalidate();
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateParent(Class... parentTypes) {
        try {
            getStatement().validateParent(parentTypes);
            validate();
        } catch (IllegalStateException e) {
            invalidate();
        }
    }

    private void validate() {
        setStyle(null);
    }

    private void invalidate() {
        setStyle("-fx-background-color: red;");
    }

    public boolean isValid() {
        return !"-fx-background-color: red;".equals(getStyle());
    }

    public Class<?> getReturnType() {
        return BlockRegistry.getExpression(getClass()).getReturnType();
    }

    public StatementBlock getStatement() {
        return ((ExpressionParameter) getParent()).getStatement();
    }

    @Override
    public List<BlockParameter> getParameters() {
        return parameters;
    }
}
