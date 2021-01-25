package com.gmail.visualbukkit.blocks.components;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.expressions.ExprBoolean;
import com.gmail.visualbukkit.blocks.expressions.ExprNewList;
import com.gmail.visualbukkit.blocks.expressions.ExprNumber;
import com.gmail.visualbukkit.blocks.expressions.ExprString;
import com.gmail.visualbukkit.gui.ContextMenuManager;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.ElementInspector;
import com.gmail.visualbukkit.gui.UndoManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.gmail.visualbukkit.util.PropertyGridPane;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PopOver;
import org.json.JSONObject;

import java.util.*;

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
        popOver.setAnimated(false);

        TextField searchField = new TextField();
        CenteredHBox searchBox = new CenteredHBox(5, new Text("Search:"), searchField);
        searchBox.setPadding(new Insets(3));
        searchField.setPrefWidth(200);
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
                popOver.setArrowLocation(e.getSceneY() > VisualBukkit.getInstance().getScene().getHeight() / 2 ?
                        PopOver.ArrowLocation.BOTTOM_LEFT :
                        PopOver.ArrowLocation.TOP_LEFT);
                popOver.show(this);
            }
            e.consume();
        });

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> {
            if (!pasteItem.isDisable()) {
                ExpressionBlock<?> expr = (ExpressionBlock<?>) CopyPasteManager.paste();
                UndoManager.run(new UndoManager.RevertableAction() {
                    @Override
                    public void run() {
                        setExpression(expr);
                    }
                    @Override
                    public void revert() {
                        setExpression(null);
                    }
                });
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(pasteItem);
        contextMenu.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
            BlockDefinition<?> copied = CopyPasteManager.peek();
            pasteItem.setDisable(!(copied instanceof ExpressionDefinition) || !expressionCache.get(returnType).contains(copied));
        });

        if (returnType == String.class) {
            MenuItem stringItem = new MenuItem("Insert String");
            contextMenu.getItems().add(stringItem);
            stringItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    setExpression(new ExprString());
                }
                @Override
                public void revert() {
                    setExpression(null);
                }
            }));
        } else if (returnType == boolean.class) {
            MenuItem booleanItem = new MenuItem("Insert Boolean");
            contextMenu.getItems().add(booleanItem);
            booleanItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    setExpression(new ExprBoolean());
                }
                @Override
                public void revert() {
                    setExpression(null);
                }
            }));
        } else if (returnType == List.class) {
            MenuItem newListItem = new MenuItem("Insert New List");
            contextMenu.getItems().add(newListItem);
            newListItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    setExpression(new ExprNewList());
                }
                @Override
                public void revert() {
                    setExpression(null);
                }
            }));
        } else if (TypeHandler.isNumber(returnType)) {
            MenuItem numberItem = new MenuItem("Insert Number");
            contextMenu.getItems().add(numberItem);
            numberItem.setOnAction(e -> UndoManager.run(new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    setExpression(new ExprNumber());
                }
                @Override
                public void revert() {
                    setExpression(null);
                }
            }));
        }

        setOnContextMenuRequested(e -> {
            if (!popOver.isShowing()) {
                expressions.setPredicate(null);
                ContextMenuManager.show(this, contextMenu, e);
            } else {
                e.consume();
            }
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
            setExpression(def.createBlock(obj.optJSONObject("expression")));
        }
    }

    public void setExpression(ExpressionBlock<?> expression) {
        this.expression = expression;
        if (expression != null) {
            getChildren().setAll(expression);
        } else {
            getChildren().setAll(promptLabel);
        }
        StatementBlock statement = getStatement();
        if (statement != null) {
            statement.update();
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

    private class ExpressionCell extends ListCell<ExpressionDefinition<?>> implements ElementInspector.Inspectable {

        public ExpressionCell(PopOver popOver) {
            setOnMouseClicked(e -> {
                ExpressionDefinition<?> def = getItem();
                if (def != null) {
                    VisualBukkit.getInstance().getElementInspector().inspect(this);
                    if (e.getClickCount() == 2) {
                        UndoManager.run(new UndoManager.RevertableAction() {
                            @Override
                            public void run() {
                                setExpression(def.createBlock(null));
                                popOver.hide();
                            }
                            @Override
                            public void revert() {
                                setExpression(null);
                            }
                        });
                    }
                }
            });
        }

        @Override
        protected void updateItem(ExpressionDefinition<?> item, boolean empty) {
            super.updateItem(item, empty);
            setText(item != null ? item.toString() : "");
        }

        @Override
        public Pane createInspectorPane() {
            PropertyGridPane gridPane = new PropertyGridPane();
            gridPane.addProperty("Name", getItem().getName());
            gridPane.addProperty("Description", getItem().getDescription());
            gridPane.addProperty("Return type", TypeHandler.getUserFriendlyName(returnType));
            return gridPane;
        }
    }
}
