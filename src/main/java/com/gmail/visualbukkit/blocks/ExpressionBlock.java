package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import com.gmail.visualbukkit.blocks.expressions.ExprBooleanAnd;
import com.gmail.visualbukkit.blocks.expressions.ExprBooleanOr;
import com.gmail.visualbukkit.blocks.expressions.ExprCombineStrings;
import com.gmail.visualbukkit.blocks.expressions.ExprNegateBoolean;
import com.gmail.visualbukkit.gui.ContextMenuManager;
import com.gmail.visualbukkit.gui.ElementInspector;
import com.gmail.visualbukkit.gui.UndoManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.gmail.visualbukkit.util.PropertyGridPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
        copyItem.setOnAction(e -> copy());
        cutItem.setOnAction(e -> cut());
        deleteItem.setOnAction(e -> delete());
        contextMenu.getItems().addAll(copyItem, cutItem, deleteItem);

        MenuItem addStringItem = new MenuItem("Add String");
        addStringItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
            ExpressionParameter exprParameter;
            @Override
            public void run() {
                exprParameter = getExpressionParameter();
                ExprCombineStrings expr = new ExprCombineStrings();
                exprParameter.setExpression(expr);
                expr.getString1().setExpression(ExpressionBlock.this);
            }
            @Override
            public void revert() {
                exprParameter.setExpression(ExpressionBlock.this);
            }
        }));

        MenuItem addAndItem = new MenuItem("Add 'And'");
        addAndItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
            ExpressionParameter exprParameter;
            @Override
            public void run() {
                exprParameter = getExpressionParameter();
                ExprBooleanAnd expr = new ExprBooleanAnd();
                exprParameter.setExpression(expr);
                expr.getBoolean1().setExpression(ExpressionBlock.this);
            }
            @Override
            public void revert() {
                exprParameter.setExpression(ExpressionBlock.this);
            }
        }));

        MenuItem addOrItem = new MenuItem("Add 'Or'");
        addOrItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
            ExpressionParameter exprParameter;
            @Override
            public void run() {
                exprParameter = getExpressionParameter();
                ExprBooleanOr expr = new ExprBooleanOr();
                exprParameter.setExpression(expr);
                expr.getBoolean1().setExpression(ExpressionBlock.this);
            }
            @Override
            public void revert() {
                exprParameter.setExpression(ExpressionBlock.this);
            }
        }));

        MenuItem negateItem = new MenuItem("Negate");
        negateItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
            ExpressionParameter exprParameter;
            @Override
            public void run() {
                exprParameter = getExpressionParameter();
                ExprNegateBoolean expr = new ExprNegateBoolean();
                exprParameter.setExpression(expr);
                expr.getBoolean().setExpression(ExpressionBlock.this);
            }
            @Override
            public void revert() {
                exprParameter.setExpression(ExpressionBlock.this);
            }
        }));

        setOnContextMenuRequested(e -> {
            if (getExpressionParameter().getReturnType() == String.class) {
                if (!contextMenu.getItems().contains(addStringItem)) {
                    contextMenu.getItems().add(addStringItem);
                }
            } else {
                contextMenu.getItems().remove(addStringItem);
            }
            if (getExpressionParameter().getReturnType() == boolean.class) {
                if (!contextMenu.getItems().contains(addAndItem)) {
                    contextMenu.getItems().addAll(addAndItem, addOrItem, negateItem);
                }
            } else {
                contextMenu.getItems().removeAll(addAndItem, addOrItem, negateItem);
            }
            ContextMenuManager.show(this, contextMenu, e);
        });
    }

    protected final void init(Object... components) {
        for (Object component : components) {
            if (component instanceof String) {
                getChildren().add(new Text((String) component));
            } else if (component instanceof Class) {
                ExpressionParameter exprParameter = new ExpressionParameter((Class<?>) component);
                getChildren().add(exprParameter);
                component = exprParameter;
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
        StatementBlock statement = getStatement();
        if (statement != null) {
            statement.validateStructure(message, structureTypes);
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateEvent(String message, Class... eventTypes) {
        StatementBlock statement = getStatement();
        if (statement != null) {
            statement.validateEvent(message, eventTypes);
        }
    }

    @SuppressWarnings("rawtypes")
    protected final void validateParent(String message, Class... parentTypes) {
        StatementBlock statement = getStatement();
        if (statement != null) {
            statement.validateParent(message, parentTypes);
        }
    }

    @Override
    public void update() {
        CodeBlock.super.update();
    }

    @Override
    public void delete() {
        ExpressionParameter exprParameter = getExpressionParameter();
        if (exprParameter != null) {
            UndoManager.run(new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    exprParameter.setExpression(null);
                }
                @Override
                public void revert() {
                    exprParameter.setExpression(ExpressionBlock.this);
                }
            });
        }
    }

    @Override
    public PropertyGridPane createInspectorPane() {
        PropertyGridPane gridPane = CodeBlock.super.createInspectorPane();
        gridPane.addProperty("Return type", TypeHandler.getUserFriendlyName(getDefinition().getReturnType()));
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

    public ExpressionParameter getExpressionParameter() {
        return (ExpressionParameter) getParent();
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
