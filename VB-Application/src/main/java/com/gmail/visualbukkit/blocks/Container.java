package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.css.PseudoClass;
import javafx.scene.control.Separator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Container extends Statement {

    private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

    public Container(String id) {
        super(id);
    }

    public static abstract class Block extends Statement.Block {

        private ChildConnector childConnector = new ChildConnector(this);

        public Block(Container container, BlockParameter... parameters) {
            super(container, parameters);

            getSyntaxBox().getChildren().addAll(new Separator(), childConnector);
            getSyntaxBox().getStyleClass().remove("statement-block");
            getSyntaxBox().getStyleClass().add("container-block");

            getSyntaxBox().addEventHandler(MouseEvent.DRAG_DETECTED, e -> childConnector.setAcceptingConnections(false));
            addEventHandler(DragEvent.DRAG_DONE, e -> childConnector.setAcceptingConnections(true));
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

        @Override
        protected void setAcceptingConnections(boolean state) {
            super.setAcceptingConnections(state);
            childConnector.setAcceptingConnections(state);
            if (childConnector.hasConnection()) {
                childConnector.getConnected().setAcceptingConnections(state);
            }
        }

        public ChildConnector getChildConnector() {
            return childConnector;
        }
    }

    public static class ChildConnector extends StatementConnector {

        public ChildConnector(CodeBlock<?> owner) {
            super(owner);
        }
    }
}
