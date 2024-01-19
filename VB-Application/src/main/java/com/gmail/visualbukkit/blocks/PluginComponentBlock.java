package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.*;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

public non-sealed abstract class PluginComponentBlock extends Block {

    private final StatementHolder childStatementHolder = new StatementHolder(this);

    public PluginComponentBlock() {
        getStyleClass().add("plugin-component-block");

        ActionMenuItem pasteItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_after"), e -> UndoManager.current().execute(() -> childStatementHolder.addFirst(CopyPasteManager.pasteStatement())));
        ActionMenuItem collapseItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.collapse_all"), e -> childStatementHolder.setCollapsedRecursive(true));
        ActionMenuItem expandItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.expand_all"), e -> childStatementHolder.setCollapsedRecursive(false));
        ActionMenuItem deleteItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete"), e -> delete());
        pasteItem.disableProperty().bind(CopyPasteManager.statementCopiedProperty().not());
        getContextMenu().getItems().addAll(pasteItem, collapseItem, expandItem, new SeparatorMenuItem(), deleteItem);

        getContextMenu().setOnShowing(e -> {
            collapseItem.setDisable(childStatementHolder.isEmpty());
            expandItem.setDisable(collapseItem.isDisable());
        });
    }

    @Override
    public void delete() {
        ProjectManager.current().promptDeletePluginComponent(ProjectManager.current().getPluginComponent(this));
    }

    @Override
    public void updateState() {
        super.updateState();
        childStatementHolder.forEach(StatementBlock::updateState);
    }

    public abstract void prepareBuild(BuildInfo buildInfo);

    public String generateChildrenJava(BuildInfo buildInfo) {
        String childrenJava = childStatementHolder.generateJava(buildInfo);
        return buildInfo.getLocalVariableDeclarations() + childrenJava;
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        for (StatementBlock block : childStatementHolder) {
            json.append("statements", block.serialize());
        }
        return json;
    }

    @Override
    public void deserialize(JSONObject json) {
        super.deserialize(json);
        JSONArray statements = json.optJSONArray("statements");
        if (statements != null) {
            for (Object obj : statements) {
                if (obj instanceof JSONObject statementJson) {
                    childStatementHolder.addLast(BlockRegistry.newStatement(statementJson));
                }
            }
        }
    }

    public StatementHolder getChildStatementHolder() {
        return childStatementHolder;
    }

    @BlockDefinition(id = "unknown-plugin-component", name = "Unknown Plugin Component")
    public static class Unknown extends PluginComponentBlock {

        private JSONObject json;

        @Override
        public void prepareBuild(BuildInfo buildInfo) {}

        @Override
        public JSONObject serialize() {
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            this.json = json;
        }
    }
}
