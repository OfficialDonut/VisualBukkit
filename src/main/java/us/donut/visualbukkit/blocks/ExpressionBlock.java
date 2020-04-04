package us.donut.visualbukkit.blocks;

import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
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

        setOnContextMenuRequested(e -> {
            getContextMenu().show(this, e.getScreenX(), e.getScreenY());
            ExpressionParameter expressionParameter = (ExpressionParameter) getParent();
            if (getReturnType() == String.class || expressionParameter.getReturnType() == String.class) {
                if (!getContextMenu().getItems().contains(addStringItem)) {
                    getContextMenu().getItems().add(addStringItem);
                }
            } else {
                getContextMenu().getItems().remove(addStringItem);
            }
            e.consume();
        });
    }

    public Class<?> getReturnType() {
        return BlockRegistry.getInfo(this).getReturnType();
    }
}
