package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.ui.*;
import javafx.css.PseudoClass;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.input.DragEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Container extends Statement {

    public Container(String id) {
        super(id);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Statement.Block {

        private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");
        private StatementHolder childHolder = new StatementHolder();
        private StatementConnector childConnector = new StatementConnector(block -> UndoManager.run(childHolder.addFirst(block)));

        public Block(Statement statement, BlockParameter... parameters) {
            super(statement, parameters);

            getBody().getStyleClass().remove("statement-block");
            getBody().getStyleClass().add("container-block");

            getBody().getChildren().addAll(new Separator(), new StyleableVBox(childConnector, childHolder));

            getBody().addEventHandler(DragEvent.DRAG_OVER, e -> {
                Bounds bounds = childHolder.localToScreen(childHolder.getBoundsInLocal());
                if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                    double deltaY = e.getScreenY() - bounds.getMinY();
                    if (deltaY > 0 && deltaY < childConnector.getMaxHeight()) {
                        childConnector.show();
                        e.consume();
                        return;
                    }
                }
                if (!childHolder.getChildren().isEmpty()) {
                    if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                        double deltaY = e.getScreenY() - bounds.getMaxY();
                        if (deltaY > 0 && deltaY < childConnector.getMaxHeight()) {
                            childHolder.getLast().getStatementConnector().show();
                        }
                    }
                }
                e.consume();
            });

            ActionMenuItem pasteInsideItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_inside"), e -> UndoManager.run(childHolder.addFirst(CopyPasteManager.pasteStatement())));
            pasteInsideItem.disableProperty().bind(getContextMenu().getItems().get(getContextMenu().getItems().size() - 1).disableProperty());
            getContextMenu().getItems().add(pasteInsideItem);
        }

        @Override
        public void update() {
            super.update();
            for (Statement.Block block : childHolder.getBlocks()) {
                block.update();
            }
            int level = 0;
            Parent parent = getParent();
            while (parent != null) {
                if (parent instanceof Container.Block) {
                    level++;
                }
                parent = parent.getParent();
            }
            getBody().pseudoClassStateChanged(NESTED_STYLE_CLASS, level % 2 == 1);
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            for (Statement.Block block : childHolder.getBlocks()) {
                block.prepareBuild(buildContext);
            }
        }

        public String getChildJava() {
            StringBuilder builder = new StringBuilder();
            for (Statement.Block block : childHolder.getBlocks()) {
                builder.append(block.toJava());
            }
            return builder.toString();
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            for (Statement.Block block : childHolder.getBlocks()) {
                json.append("children", block.serialize());
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            JSONArray childArray = json.optJSONArray("children");
            if (childArray != null) {
                for (Object obj : childArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject childJson = (JSONObject) obj;
                        Statement statement = BlockRegistry.getStatement(childJson.optString("="));
                        if (statement != null) {
                            childHolder.addLast(statement.createBlock(childJson)).run();
                        }
                    }
                }
            }
        }

        @Override
        protected void setAcceptingConnections(boolean state) {
            super.setAcceptingConnections(state);
            childConnector.setAcceptingConnections(state);
            for (Statement.Block block : childHolder.getBlocks()) {
                block.setAcceptingConnections(state);
            }
        }

        public StatementHolder getChildHolder() {
            return childHolder;
        }

        public StatementConnector getChildConnector() {
            return childConnector;
        }

        @Override
        public Container getDefinition() {
            return (Container) super.getDefinition();
        }
    }
}
