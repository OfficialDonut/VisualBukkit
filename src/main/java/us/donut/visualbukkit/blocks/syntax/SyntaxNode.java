package us.donut.visualbukkit.blocks.syntax;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode extends VBox {

    private List<BlockParameter> parameters = new ArrayList<>();

    public SyntaxNode(Object... components) {
        super(5);
        getChildren().add(new SyntaxLine(components));
    }

    public SyntaxNode line(Object... components) {
        SyntaxLine syntaxLine = new SyntaxLine(components);
        syntaxLine.setPadding(new Insets(0, 0, 0, 15));
        getChildren().add(syntaxLine);
        return this;
    }

    public void add(Object... components) {
        add(0, components);
    }

    public void add(int lineIndex, Object... components) {
        ((SyntaxLine) getChildren().get(lineIndex)).add(components);
    }

    public void clear() {
        for (Node node : getChildren()) {
            ((SyntaxLine) node).getChildren().clear();
        }
    }

    public List<BlockParameter> getParameters() {
        return parameters;
    }

    private class SyntaxLine extends HBox {

        private SyntaxLine(Object... components) {
            getStyleClass().add("syntax-node");
            add(components);
        }

        private void add(Object... components) {
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
    }
}
