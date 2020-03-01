package us.donut.visualbukkit.blocks;

import javafx.scene.Parent;
import us.donut.visualbukkit.blocks.expressions.ExprEmptyParameter;

public abstract class ExpressionBlock extends CodeBlock {

    public ExpressionBlock() {
        if (!(this instanceof ExprEmptyParameter)) {
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
        }
    }

    public abstract Class<?> getReturnType();

    public interface Changeable {

        String change(ChangeType changeType, String delta);
    }

    public enum ChangeType {
        SET, DELETE, ADD, REMOVE
    }
}
