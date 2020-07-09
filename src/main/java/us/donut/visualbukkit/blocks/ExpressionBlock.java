package us.donut.visualbukkit.blocks;

import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import us.donut.visualbukkit.blocks.expressions.ExprAnd;
import us.donut.visualbukkit.blocks.expressions.ExprOr;
import us.donut.visualbukkit.blocks.expressions.ExprStringConcatenation;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;

public abstract class ExpressionBlock<T> extends CodeBlock {

    public ExpressionBlock() {
        setOnMouseMoved(e -> {
            setStyle("-fx-background-color: yellow;");
            Parent parent = getParent();
            while (!(parent instanceof StatementBlock)) {
                if (parent instanceof ExpressionBlock) {
                    parent.setStyle(null);
                }
                parent = parent.getParent();
            }
            e.consume();
        });

        setOnMouseExited(e -> {
            setStyle(null);
            e.consume();
        });

        MenuItem addStringItem = new MenuItem("Add string");
        addStringItem.setOnAction(e -> {
            ExprStringConcatenation concatExpr = new ExprStringConcatenation();
            ExpressionParameter expressionParameter = (ExpressionParameter) getParent();
            expressionParameter.setExpression(concatExpr);
            ((ExpressionParameter) concatExpr.getParameter(0)).setExpression(this);
        });

        MenuItem addAndItem = new MenuItem("Add 'and'");
        addAndItem.setOnAction(e -> {
            ExprAnd andExpr = new ExprAnd();
            ExpressionParameter expressionParameter = (ExpressionParameter) getParent();
            expressionParameter.setExpression(andExpr);
            ((ExpressionParameter) andExpr.getParameter(0)).setExpression(this);
        });

        MenuItem addOrItem = new MenuItem("Add 'or'");
        addOrItem.setOnAction(e -> {
            ExprOr orExpr = new ExprOr();
            ExpressionParameter expressionParameter = (ExpressionParameter) getParent();
            expressionParameter.setExpression(orExpr);
            ((ExpressionParameter) orExpr.getParameter(0)).setExpression(this);
        });

        setOnContextMenuRequested(e -> {
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
            ExpressionParameter expressionParameter = (ExpressionParameter) getParent();
            if (getReturnType() == String.class || expressionParameter.getReturnType() == String.class) {
                if (!contextMenu.getItems().contains(addStringItem)) {
                    contextMenu.getItems().add(addStringItem);
                }
            } else {
                contextMenu.getItems().remove(addStringItem);
            }
            if (getReturnType() == boolean.class || expressionParameter.getReturnType() == boolean.class) {
                if (!contextMenu.getItems().contains(addAndItem)) {
                    contextMenu.getItems().addAll(addAndItem, addOrItem);
                }
            } else {
                contextMenu.getItems().removeAll(addAndItem, addOrItem);
            }
            Parent parent = getParent();
            while (parent != null) {
                if (parent instanceof StatementBlock) {
                    ((StatementBlock) parent).contextMenu.hide();
                    break;
                }
                parent = parent.getParent();
            }
            e.consume();
        });
    }

    public Class<?> getReturnType() {
        return BlockRegistry.getInfo(this).getReturnType();
    }
}
