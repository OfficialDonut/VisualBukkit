package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.ui.*;
import javafx.css.PseudoClass;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.input.TransferMode;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Container extends Statement {

    public Container(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Statement.Block {

        private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

        private StatementHolder childHolder;
        private StatementConnector childConnector;

        public Block(Statement statement, BlockParameter<?>... parameters) {
            super(statement, parameters);

            childConnector = new StatementConnector() {
                @Override
                public void accept(Statement.Block block) {
                    UndoManager.run(childHolder.addFirst(block));
                }
            };
            childHolder = new StatementHolder(childConnector);

            Separator separator = new Separator();
            getBody().getChildren().addAll(separator, new StyleableVBox(childConnector, childHolder));
            getBody().getStyleClass().remove("statement-block");
            getBody().getStyleClass().add("container-block");

            getBody().setOnDragOver(e -> {
                Object source = e.getGestureSource();
                if (source instanceof StatementSource || source instanceof Statement.Block) {
                    if (e.getY() > separator.getBoundsInParent().getMinY()) {
                        Bounds bounds = childHolder.localToScreen(childHolder.getBoundsInLocal());
                        (e.getScreenY() < bounds.getMinY() ? childConnector : childHolder.getLastEnabledConnector()).show();
                    } else {
                        getStatementHolder().getPreviousConnector(this).show();
                    }
                    e.acceptTransferModes(TransferMode.ANY);
                    e.consume();
                }
            });

            getBody().setOnDragDropped(e -> {
                Object source = e.getGestureSource();
                if (source instanceof StatementSource || source instanceof Statement.Block) {
                    StatementConnector.getCurrent().accept(source instanceof StatementSource s ? s.getBlockDefinition().createBlock() : (Statement.Block) source);
                    SoundManager.SNAP.play();
                    e.setDropCompleted(true);
                    e.consume();
                }
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
        public void toggleExpressionParameters(boolean state) {
            super.toggleExpressionParameters(state);
            for (Statement.Block block : childHolder.getBlocks()) {
                block.toggleExpressionParameters(state);
            }
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            childHolder.setDebugMode(buildContext.isDebugMode());
            for (Statement.Block block : childHolder.getBlocks()) {
                block.prepareBuild(buildContext);
            }
        }

        public String getChildJava() {
            return childHolder.toJava();
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
                    if (obj instanceof JSONObject childJson) {
                        Statement statement = BlockRegistry.getStatement(childJson.optString("="));
                        if (statement != null) {
                            childHolder.addLast(statement.createBlock(childJson)).run();
                        }
                    }
                }
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
