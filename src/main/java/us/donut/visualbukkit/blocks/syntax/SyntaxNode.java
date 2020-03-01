package us.donut.visualbukkit.blocks.syntax;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode extends HBox {

    private List<BlockParameter> parameters = new ArrayList<>();

    public SyntaxNode(Object... components) {
        getStyleClass().add("syntax-node");
        add(components);
    }

    public void add(Object... components) {
        for (Object component : components) {
            if (component instanceof BlockParameter) {
                parameters.add((BlockParameter) component);
            }
            if (component instanceof Node) {
                getChildren().add((Node) component);
            }
            if (component instanceof String) {
                getChildren().add(new Text((String) component));
            } else if (component instanceof Class) {
                ExpressionParameter expressionParameter = new ExpressionParameter((Class<?>) component);
                parameters.add(expressionParameter);
                getChildren().add(expressionParameter);
            }
        }
    }

    public List<BlockParameter> getParameters() {
        return parameters;
    }
}
