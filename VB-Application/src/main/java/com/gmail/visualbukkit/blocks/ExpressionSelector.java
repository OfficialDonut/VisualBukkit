package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.ui.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class ExpressionSelector {

    private static Map<String, PopOver> popOvers = new HashMap<>();
    private static Set<Expression> expressions;
    private static ExpressionParameter currentExpressionParameter;

    public static void show(ExpressionParameter expressionParameter, PopOver.ArrowLocation arrowLocation) {
        PopOver selector = popOvers.computeIfAbsent(expressionParameter.getType().getDisplayClassName(), k -> {
            ObservableList<Expression> expressionList = FXCollections.observableArrayList();
            int i = 0;
            for (Expression expression : expressions) {
                expressionList.add(expression.getReturnType().getDisplayClassName().equals(k) ? i++ : expressionList.size(), expression);
            }
            expressionList.add(i, new ListSeparatorExpression());

            FilteredList<Expression> filteredExpressionList = new FilteredList<>(expressionList);
            ListView<Expression> listView = new ListView<>(filteredExpressionList);
            TextField searchField = new TextField();

            Predicate<Expression> filter = expr -> expr instanceof ListSeparatorExpression || (StringUtils.containsIgnoreCase(expr.getFullTitle(), searchField.getText()));
            searchField.textProperty().addListener((o, oldValue, newValue) -> filteredExpressionList.setPredicate(filter::test));

            PopOver popOver = new PopOver(new StyleableVBox(new StyleableHBox(new Label(LanguageManager.get("label.search")), searchField), listView));
            popOver.getStyleClass().add("expression-selector");
            popOver.setAnimated(false);
            popOver.setOnShowing(e -> {
                listView.getSelectionModel().clearSelection();
                searchField.clear();
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
        selector.show(expressionParameter.getControl());
        currentExpressionParameter = expressionParameter;
    }

    public static void setExpressions(Set<Expression> expressions) {
        popOvers.clear();
        ExpressionSelector.expressions = expressions;
    }

    private static class ListSeparatorExpression extends Expression {

        public ListSeparatorExpression() {
            super("", "", "", "");
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
            return "=====================";
        }
    }
}
