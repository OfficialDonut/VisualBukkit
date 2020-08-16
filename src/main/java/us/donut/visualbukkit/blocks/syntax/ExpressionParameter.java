package us.donut.visualbukkit.blocks.syntax;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.editor.BlockCanvas;
import us.donut.visualbukkit.editor.ContextMenuManager;
import us.donut.visualbukkit.editor.CopyPasteManager;
import us.donut.visualbukkit.editor.UndoManager;
import us.donut.visualbukkit.util.ComboBoxView;
import us.donut.visualbukkit.util.DataConfig;

import java.util.*;
import java.util.function.Predicate;

public class ExpressionParameter extends ComboBoxView<ExpressionDefinition<?>> implements BlockParameter {

    private static Map<Predicate<ExpressionDefinition<?>>, Set<ExpressionDefinition<?>>> expressionInfoMap = new HashMap<>();
    private static Map<Class<?>, Predicate<ExpressionDefinition<?>>> validators = new HashMap<>();
    private Class<?> returnType;
    private ExpressionBlock<?> expression;

    public ExpressionParameter(Class<?> returnType) {
        this(returnType, validators.computeIfAbsent(returnType, k -> expression -> TypeHandler.canConvert(expression.getReturnType(), returnType)));
    }

    public ExpressionParameter(Class<?> returnType, Predicate<ExpressionDefinition<?>> validator) {
        super("<" + TypeHandler.getUserFriendlyName(returnType) + ">");
        this.returnType = returnType;
        getStyleClass().add("expression-parameter");

        getComboBox().setConverter(new StringConverter<ExpressionDefinition<?>>() {
            @Override
            public String toString(ExpressionDefinition<?> expression) {
                return expression.getName();
            }

            @Override
            public ExpressionDefinition<?> fromString(String string) {
                return null;
            }
        });

        getComboBox().getItems().addAll(expressionInfoMap.computeIfAbsent(validator, k -> {
            Set<ExpressionDefinition<?>> expressions = new TreeSet<>(Comparator.comparing(ExpressionDefinition::getName));
            expressions.addAll(BlockRegistry.getExpressions());
            expressions.removeIf(expression -> !validator.test(expression));
            return expressions;
        }));

        getListView().setCellFactory(new Callback<ListView<ExpressionDefinition<?>>, ListCell<ExpressionDefinition<?>>>() {
            @Override
            public ListCell<ExpressionDefinition<?>> call(ListView<ExpressionDefinition<?>> param) {
                TextFieldListCell<ExpressionDefinition<?>> cell = new TextFieldListCell<ExpressionDefinition<?>>() {
                    @Override
                    public void updateItem(ExpressionDefinition<?> expression, boolean empty) {
                        super.updateItem(expression, empty);
                        setTooltip(expression != null && !empty ? new Tooltip(expression.getDescription()) : null);
                    }
                };
                cell.setConverter(getComboBox().getConverter());
                return cell;
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem invalidPasteItem = new MenuItem("Paste");
        MenuItem pasteItem = new MenuItem("Paste");
        invalidPasteItem.setStyle("-fx-text-fill: white; -fx-opacity: 0.5;");
        pasteItem.setStyle("-fx-text-fill: white;");
        pasteItem.setOnAction(e -> {
            UndoManager.capture();
            expression = (ExpressionBlock<?>) CopyPasteManager.paste();
            getComboBox().setValue(BlockRegistry.getExpression(expression.getClass()));
        });
        contextMenu.getItems().add(pasteItem);
        setOnContextMenuRequested(e -> {
            BlockDefinition<?> copied = CopyPasteManager.getCopied();
            contextMenu.getItems().set(0, copied instanceof ExpressionDefinition && validator.test((ExpressionDefinition<?>) copied) ? pasteItem : invalidPasteItem);
            ContextMenuManager.show(this, contextMenu, e);
        });
    }

    @Override
    protected void onSelection(ExpressionDefinition<?> value) {
        if (value != null) {
            if (expression == null) {
                UndoManager.capture();
                expression = value.createBlock();
            }
            getStyleClass().clear();
            getChildren().setAll(expression);
        } else {
            expression = null;
            getStyleClass().addAll("expression-parameter", "combo-box-view");
            getChildren().setAll(getPromptLabel(), getArrowLabel());
        }
        StatementBlock statement = getStatement();
        if (statement != null) {
            statement.update();
        }
    }

    @Override
    public void update() {
        if (expression != null) {
            expression.update();
        }
    }

    @Override
    public String toJava() {
        return expression != null && expression.isValid() ?
                TypeHandler.convert(expression.getReturnType(), returnType, expression.toJava()) :
                "((" + returnType.getCanonicalName() + ")" + (returnType.isPrimitive() ? "0" : "null") + ")";
    }

    @Override
    public void saveTo(DataConfig config) {
        if (expression != null) {
            config.set("=", expression.getIdentifier());
            expression.saveTo(config);
        }
    }

    @Override
    public void loadFrom(DataConfig config) {
        ExpressionDefinition<?> expression = BlockRegistry.getExpression(config.getString("="));
        if (expression != null) {
            this.expression = expression.createBlock(config);
            getComboBox().setValue(expression);
        }
    }

    public StatementBlock getStatement() {
        Parent parent = getParent();
        while (parent != null && (!(parent instanceof BlockCanvas))) {
            if (parent instanceof StatementBlock) {
                return (StatementBlock) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public ExpressionBlock<?> getExpression() {
        return expression;
    }
}
