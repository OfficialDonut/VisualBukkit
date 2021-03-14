package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Container extends Statement {

    private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

    public Container(String id) {
        super(id);
    }

    public static abstract class Block extends Statement.Block {

        private VBox childPlaceholder = new VBox();
        private VBox childHolder = new VBox();
        private ChildConnector childConnector = new ChildConnector(this, childHolder);

        public Block(Container container, BlockParameter... parameters) {
            super(container, parameters);

            VBox childBox = new VBox(childPlaceholder, childConnector, childHolder);
            childBox.setStyle("-fx-background-color: -fx-background;");
            childPlaceholder.setStyle("-fx-background-color: -fx-background;");
            childPlaceholder.setPrefHeight(childConnector.getMaxHeight() - 1);
            getSyntaxBox().getChildren().addAll(new Separator(), childBox);
            getSyntaxBox().getStyleClass().remove("statement-block");
            getSyntaxBox().getStyleClass().add("container-block");

            getSyntaxBox().addEventHandler(MouseEvent.DRAG_DETECTED, e -> childConnector.setAcceptingConnections(false));
            addEventHandler(DragEvent.DRAG_DONE, e -> childConnector.setAcceptingConnections(true));

            childHolder.getChildren().addListener((ListChangeListener<Node>) change -> childPlaceholder.setPrefHeight(childHolder.getChildren().isEmpty() ? childConnector.getMaxHeight() - 1 : 0));

            childConnector.prefHeightProperty().addListener((o, oldValue, newValue) -> {
                if (!childConnector.hasConnection()) {
                    childPlaceholder.setPrefHeight(newValue.doubleValue() == 0 ? childConnector.getMaxHeight() - 1 : 0);
                }
            });

            getSyntaxBox().setOnDragOver(e -> {
                if (getPrevious().isAcceptingConnections()) {
                    Bounds bounds = getPrevious().localToScreen(getPrevious().getBoundsInLocal());
                    if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                        double deltaY = e.getScreenY() - bounds.getMinY();
                        if (deltaY > 0 && deltaY < getPrevious().getMaxHeight()) {
                            getPrevious().show();
                        }
                    }
                }
                if (childConnector.hasConnection()) {
                    StatementConnector connector = childConnector.getConnected().getLast().getNext();
                    if (connector.isAcceptingConnections()) {
                        Bounds bounds = connector.localToScreen(connector.getBoundsInLocal());
                        if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                            double deltaY = e.getScreenY() - bounds.getMinY();
                            if (deltaY > 0 && deltaY < connector.getMaxHeight()) {
                                connector.show();
                            }
                        }
                    }
                } else if (childConnector.isAcceptingConnections()
                        && childConnector.getPrefHeight() == 0
                        && childPlaceholder.localToScreen(childPlaceholder.getBoundsInLocal()).contains(e.getScreenX(), e.getScreenY())) {
                    childConnector.show();
                }
            });
        }

        @Override
        public void collapseStack(boolean collapse) {
            super.collapseStack(collapse);
            if (childConnector.hasConnection()) {
                childConnector.getConnected().collapseStack(collapse);
            }
        }

        @Override
        public void update() {
            super.update();
            if (childConnector.hasConnection()) {
                childConnector.getConnected().update();
            }
            int level = 0;
            Statement.Block block = this;
            while (block != null && block.getPrevious() != null) {
                if (block.getPrevious() instanceof ChildConnector) {
                    level++;
                }
                CodeBlock<?> owner = block.getPrevious().getOwner();
                block = owner instanceof Statement.Block ? (Statement.Block) owner : null;
            }
            getSyntaxBox().pseudoClassStateChanged(NESTED_STYLE_CLASS, level % 2 == 1);
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            if (childConnector.hasConnection()) {
                childConnector.getConnected().prepareBuild(buildContext);
            }
        }

        public String getChildJava() {
            StringBuilder builder = new StringBuilder();
            Statement.Block child = childConnector.getConnected();
            while (child != null) {
                builder.append(child.toJava());
                child = child.getNext().getConnected();
            }
            return builder.toString();
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            Statement.Block child = childConnector.getConnected();
            while (child != null) {
                json.append("children", child.serialize());
                child = child.getNext().getConnected();
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            JSONArray childArray = json.optJSONArray("children");
            if (childArray != null) {
                StatementConnector connector = childConnector;
                for (Object obj : childArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject childJson = (JSONObject) obj;
                        Statement statement = BlockRegistry.getStatement(childJson.optString("="));
                        if (statement != null) {
                            Statement.Block block = statement.createBlock(childJson);
                            connector.connect(block).run();
                            connector = block.getNext();
                        }
                    }
                }
            }
        }

        public ChildConnector getChildConnector() {
            return childConnector;
        }
    }

    public static class ChildConnector extends StatementConnector {

        public ChildConnector(CodeBlock<?> owner, Pane blockHolder) {
            super(owner, blockHolder);
        }
    }
}
