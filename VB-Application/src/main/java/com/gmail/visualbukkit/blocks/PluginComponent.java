package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.ui.*;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public abstract class PluginComponent extends Statement {

    public PluginComponent(String id) {
        super(id);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Statement.Block {

        private StatementHolder statementHolder = new StatementHolder();
        private Tab tab;

        public Block(PluginComponent pluginComponent, BlockParameter... parameters) {
            super(pluginComponent);

            getBody().getStyleClass().remove("statement-block");
            getBody().getStyleClass().add("plugin-component-block");
            getBody().setOnDragDetected(Event::consume);
            addParameterLines(pluginComponent.getParameterNames(), parameters);

            statementHolder.getChildren().add(this);
            Pane pane = new Pane(this, statementHolder);
            tab = new Tab(pluginComponent.getTitle(), new ScrollPane(pane));

            pane.setOnDragOver(e -> {
                Bounds bounds = statementHolder.localToScreen(statementHolder.getBoundsInLocal());
                if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                    double deltaY = e.getScreenY() - bounds.getMaxY();
                    if (deltaY > 0 && deltaY < getStatementConnector().getMaxHeight()) {
                        statementHolder.getLast().getStatementConnector().show();
                    }
                }
                e.consume();
            });

            ActionMenuItem collapseAllItem = new ActionMenuItem(LanguageManager.get("context_menu.collapse_all"), e -> recursiveCollapse(statementHolder, true));
            ActionMenuItem expandAllItem = new ActionMenuItem(LanguageManager.get("context_menu.expand_all"), e -> recursiveCollapse(statementHolder, false));
            ActionMenuItem pasteAfterItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_after"), e -> UndoManager.run(statementHolder.add(this, CopyPasteManager.pasteStatement())));
            getContextMenu().getItems().setAll(collapseAllItem, expandAllItem, new SeparatorMenuItem(), pasteAfterItem);

            getContextMenu().setOnShowing(e -> {
                boolean state = statementHolder.getChildren().size() == 1;
                collapseAllItem.setDisable(state);
                expandAllItem.setDisable(state);
                pasteAfterItem.setDisable(!CopyPasteManager.isStatementCopied());
            });
        }

        private void recursiveCollapse(StatementHolder statementHolder, boolean state) {
            for (Statement.Block block : statementHolder.getBlocks()) {
                if (!(block instanceof Block)) {
                    block.collapsedProperty().set(state);
                    if (block instanceof Container.Block) {
                        recursiveCollapse(((Container.Block) block).getChildHolder(), state);
                    }
                }
            }
        }

        @Override
        public void handleSelectedAction(KeyEvent e) {}

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            List<Statement.Block> children = statementHolder.getBlocks();
            for (int i = 1; i < children.size(); i++) {
                children.get(i).prepareBuild(buildContext);
            }
        }

        @Override
        public final String toJava() {
            StringBuilder builder = new StringBuilder();
            List<Statement.Block> children = statementHolder.getBlocks();
            for (int i = 1; i < children.size(); i++) {
                builder.append(children.get(i).toJava());
            }
            return builder.toString();
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            List<Statement.Block> children = statementHolder.getBlocks();
            for (int i = 1; i < children.size(); i++) {
                json.append("children", children.get(i).serialize());
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
                            statementHolder.addLast(statement.createBlock(childJson)).run();
                        }
                    }
                }
            }
        }

        public Tab getTab() {
            return tab;
        }

        @Override
        public PluginComponent getDefinition() {
            return (PluginComponent) super.getDefinition();
        }
    }

    public static class Pane extends StyleableVBox {

        private Block block;

        private Pane(Block block, StatementHolder statementHolder) {
            this.block = block;
            Region spacer = new Region();
            spacer.setPrefHeight(1000);
            getChildren().addAll(statementHolder, spacer);
            getStyleClass().add("plugin-component-pane");
        }

        public Block getBlock() {
            return block;
        }
    }
}
