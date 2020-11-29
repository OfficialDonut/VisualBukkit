package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import com.gmail.visualbukkit.gui.ContextMenuManager;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.UndoManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.gmail.visualbukkit.gui.ElementInspector;
import com.gmail.visualbukkit.util.PropertyGridPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionBlock<T> extends CenteredHBox implements CodeBlock, ElementInspector.Inspectable {

    private static ExpressionBlock<?> highlightedBlock;

    protected List<BlockParameter> parameters = new ArrayList<>();
    protected ContextMenu contextMenu = new ContextMenu();

    public ExpressionBlock() {
        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().inspect(this);
                ContextMenuManager.hide();
                e.consume();
            }
        });

        setOnMouseMoved(e -> {
            if (!equals(highlightedBlock)) {
                if (highlightedBlock != null) {
                    highlightedBlock.setHighlighted(false);
                }
                setHighlighted(true);
            }
            e.consume();
        });

        setOnMouseExited(e -> setHighlighted(false));

        MenuItem copyItem = new MenuItem("Copy");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem deleteItem = new MenuItem("Delete");
        copyItem.setOnAction(e -> CopyPasteManager.copy(this));
        cutItem.setOnAction(e -> {
            UndoManager.capture();
            CopyPasteManager.copy(this);
            ((ExpressionParameter) getParent()).setExpression(null);
        });
        deleteItem.setOnAction(e -> {
            UndoManager.capture();
            ((ExpressionParameter) getParent()).setExpression(null);
        });
        contextMenu.getItems().addAll(copyItem, cutItem, deleteItem);
        setOnContextMenuRequested(e -> ContextMenuManager.show(this, contextMenu, e));
    }

    protected final void init(Object... components) {
        for (Object component : components) {
            if (component instanceof String) {
                getChildren().add(new Text((String) component));
            } else if (component instanceof Node) {
                getChildren().add((Node) component);
            }
            if (component instanceof BlockParameter) {
                parameters.add((BlockParameter) component);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateStructure(String message, Class... structureTypes) {
        getStatement().validateStructure(message, structureTypes);
    }

    @SuppressWarnings("rawtypes")
    protected final void validateEvent(String message, Class... eventTypes) {
        getStatement().validateEvent(message, eventTypes);
    }

    @SuppressWarnings("rawtypes")
    protected final void validateParent(String message, Class... parentTypes) {
        getStatement().validateParent(message, parentTypes);
    }

    @Override
    public Pane createInspectorPane() {
        PropertyGridPane gridPane = new PropertyGridPane();
        gridPane.addProperty(0, "Name", getDefinition().getName());
        gridPane.addProperty(1, "Description", getDefinition().getDescription());
        gridPane.addProperty(2, "Return type", TypeHandler.getUserFriendlyName(getDefinition().getReturnType()));
        return gridPane;
    }

    private void setHighlighted(boolean highlight) {
        if (highlight) {
            highlightedBlock = this;
            setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            highlightedBlock = null;
            setBackground(null);
        }
    }

    public StatementBlock getStatement() {
        return ((ExpressionParameter) getParent()).getStatement();
    }

    @Override
    public List<BlockParameter> getParameters() {
        return parameters;
    }

    @Override
    public ExpressionDefinition<?> getDefinition() {
        return BlockRegistry.getExpression(this);
    }
}
