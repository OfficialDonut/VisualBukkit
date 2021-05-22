package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.StyleableHBox;
import com.gmail.visualbukkit.ui.StyleableVBox;
import com.gmail.visualbukkit.ui.UndoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;

import java.util.*;
import java.util.function.Predicate;

public class ExpressionSelector {

    private Map<String, PopOver> popOvers = new HashMap<>();
    private Set<Expression> expressions;
    private ExpressionParameter currentExpressionParameter;

    public void show(ExpressionParameter expressionParameter, PopOver.ArrowLocation arrowLocation) {
        PopOver selector = popOvers.computeIfAbsent(expressionParameter.getType().getDisplayClassName(), k -> {
            ObservableList<Expression> expressionList = FXCollections.observableArrayList();
            Set<String> titles = new HashSet<>();
            int i = 0;
            for (Expression expression : expressions) {
                if (titles.add(expression.getTitle())) {
                    expressionList.add(expression.getReturnType().getDisplayClassName().equals(k) ? i++ : expressionList.size(), expression);
                }
            }
            expressionList.add(i, new ListSeparatorExpression());

            FilteredList<Expression> filteredExpressionList = new FilteredList<>(expressionList);
            ListView<Expression> listView = new ListView<>(filteredExpressionList);
            TextField searchField = new TextField();

            Predicate<Expression> filter = expr -> expr instanceof ListSeparatorExpression || (StringUtils.containsIgnoreCase(expr.getTitle(), searchField.getText()));
            searchField.textProperty().addListener((o, oldValue, newValue) -> filteredExpressionList.setPredicate(filter::test));

            PopOver popOver = new PopOver(new StyleableVBox(new StyleableHBox(new Label(LanguageManager.get("label.search")), searchField), listView));
            popOver.getStyleClass().add("popover-selector");
            popOver.setAnimated(false);
            popOver.setOnShowing(e -> {
                listView.getSelectionModel().clearSelection();
                searchField.clear();
            });

            listView.setPlaceholder(new Label(LanguageManager.get("label.empty_list")));

            listView.setCellFactory(view -> new ListCell<>() {
                @Override
                protected void updateItem(Expression expression, boolean empty) {
                    super.updateItem(expression, empty);
                    if (expression != null) {
                        setText(expression.toString());
                        setTooltip(new Tooltip(expression.getDescription() != null ? expression.getDescription() : LanguageManager.get("tooltip.no_description")));
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            });

            listView.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null && currentExpressionParameter != null) {
                    popOver.hide();
                    UndoManager.run(newValue instanceof ListSeparatorExpression ?
                            currentExpressionParameter.clear() :
                            currentExpressionParameter.setExpression(newValue.createBlock()));
                }
            });

            return popOver;
        });

        selector.setArrowLocation(arrowLocation);
        selector.show(expressionParameter);
        currentExpressionParameter = expressionParameter;
    }

    public void setExpressions(Set<Expression> expressions) {
        popOvers.clear();
        this.expressions = expressions;
    }

    private static class ListSeparatorExpression extends Expression {

        public ListSeparatorExpression() {
            super("");
        }

        @Override
        public ClassInfo getReturnType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Block createBlock() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return "==================================================";
        }
    }
}
