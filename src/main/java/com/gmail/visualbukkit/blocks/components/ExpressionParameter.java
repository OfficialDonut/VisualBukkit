package com.gmail.visualbukkit.blocks.components;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.expressions.ExprBoolean;
import com.gmail.visualbukkit.blocks.expressions.ExprNumber;
import com.gmail.visualbukkit.blocks.expressions.ExprString;
import com.gmail.visualbukkit.gui.ContextMenuManager;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.UndoManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ExpressionParameter extends CenteredHBox implements BlockParameter {

    private static Map<Class<?>, FilteredList<ExpressionDefinition<?>>> expressionCache = new HashMap<>();

    private Class<?> returnType;
    private ExpressionBlock<?> expression;
    private Label promptLabel;

    public ExpressionParameter(Class<?> returnType) {
        this.returnType = returnType;

        FilteredList<ExpressionDefinition<?>> expressions = expressionCache.computeIfAbsent(returnType, k -> {
            Set<ExpressionDefinition<?>> set = new TreeSet<>();
            for (ExpressionDefinition<?> expression : BlockRegistry.getExpressions()) {
                if (TypeHandler.canConvert(expression.getReturnType(), returnType)) {
                    set.add(expression);
                }
            }
            return new FilteredList<>(FXCollections.observableArrayList(set), null);
        });

        PopOver popOver = new PopOver();
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
        popOver.setAnimated(false);

        TextField searchField = new TextField();
        CenteredHBox searchBox = new CenteredHBox(5, new Text("Search:"), searchField);
        searchBox.setPadding(new Insets(3));
        searchField.textProperty().addListener((observable, oldValue, newValue) -> expressions.setPredicate(expression -> expression.getName().toLowerCase().contains(searchField.getText().toLowerCase())));

        ListView<ExpressionDefinition<?>> listView = new ListView<>(expressions);
        listView.setCellFactory(view -> new ExpressionCell(popOver));
        listView.setPlaceholder(new Label("<none>"));
        popOver.setContentNode(new VBox(searchBox, listView));

        getChildren().add(promptLabel = new Label("<" + TypeHandler.getUserFriendlyName(returnType) + ">"));
        promptLabel.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(10), Insets.EMPTY)));
        promptLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.THIN)));
        promptLabel.setPadding(new Insets(0));
        promptLabel.setTextFill(Color.BLACK);

        promptLabel.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && !popOver.isShowing()) {
                ContextMenuManager.hide();
                listView.getSelectionModel().clearSelection();
                searchField.clear();
                expressions.setPredicate(null);
                popOver.show(this);
            }
            e.consume();
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem invalidPasteItem = new MenuItem("Paste");
        MenuItem pasteItem = new MenuItem("Paste");
        invalidPasteItem.setStyle("-fx-opacity: 0.5;");
        pasteItem.setOnAction(e -> {
            UndoManager.capture();
            getChildren().setAll(expression = (ExpressionBlock<?>) CopyPasteManager.paste());
            getStatement().update();
        });
        contextMenu.getItems().add(pasteItem);
        if (returnType == String.class) {
            MenuItem stringItem = new MenuItem("Insert String");
            stringItem.setOnAction(e -> setExpression(BlockRegistry.getExpression(ExprString.class)));
            contextMenu.getItems().add(stringItem);
        }
        if (returnType == boolean.class) {
            MenuItem booleanItem = new MenuItem("Insert Boolean");
            booleanItem.setOnAction(e -> setExpression(BlockRegistry.getExpression(ExprBoolean.class)));
            contextMenu.getItems().add(booleanItem);
        }
        if (TypeHandler.isNumber(returnType)) {
            MenuItem numberItem = new MenuItem("Insert Number");
            numberItem.setOnAction(e -> setExpression(BlockRegistry.getExpression(ExprNumber.class)));
            contextMenu.getItems().add(numberItem);
        }
        setOnContextMenuRequested(e -> {
            BlockDefinition<?> copied = CopyPasteManager.peek();
            contextMenu.getItems().set(0, copied instanceof ExpressionDefinition && expressionCache.get(returnType).contains(copied) ? pasteItem : invalidPasteItem);
            ContextMenuManager.show(this, contextMenu, e);
        });
    }

    @Override
    public void update() {
        if (expression != null) {
            expression.update();
        }
    }

    @Override
    public String toJava() {
        return expression != null ?
                TypeHandler.convert(expression.getDefinition().getReturnType(), returnType, expression.toJava()) :
                "((" + returnType.getCanonicalName() + ")" + (returnType.isPrimitive() ? "(Object) null" : "null") + ")";
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        if (expression != null) {
            obj.put("=", BlockRegistry.getIdentifier(expression));
            obj.put("expression", expression.serialize());
        }
        return obj;
    }

    @Override
    public void deserialize(JSONObject obj) {
        ExpressionDefinition<?> def = BlockRegistry.getExpression(obj.optString("="));
        if (def != null) {
            setExpression(def);
            JSONObject exprObj = obj.optJSONObject("expression");
            if (exprObj != null) {
                expression.deserialize(exprObj);
            }
        }
    }

    public void setExpression(ExpressionDefinition<?> expression) {
        if (expression != null) {
            getChildren().setAll(this.expression = expression.createBlock());
        } else {
            this.expression = null;
            getChildren().setAll(promptLabel);
        }
    }

    @Override
    public String toString() {
        return toJava();
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

    private class ExpressionCell extends ListCell<ExpressionDefinition<?>> {

        public ExpressionCell(PopOver popOver) {
            setOnMouseClicked(e -> {
                ExpressionDefinition<?> def = getItem();
                if (def != null) {
                    VisualBukkit.getInstance().getElementInspector().inspect(def);
                    if (e.getClickCount() == 2) {
                        UndoManager.capture();
                        ExpressionParameter.this.setExpression(def);
                        ExpressionParameter.this.getStatement().update();
                        popOver.hide();
                    }
                }
            });
        }

        @Override
        protected void updateItem(ExpressionDefinition<?> item, boolean empty) {
            super.updateItem(item, empty);
            setText(item != null ? item.toString() : "");
        }
    }
}
