package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.BuildInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringJoiner;

public non-sealed abstract class PluginComponentBlock extends Block {

    private final StatementHolder statementHolder = new StatementHolder();

    public PluginComponentBlock() {
        getStyleClass().add("plugin-component-block");
    }

    @Override
    public void updateState() {
        super.updateState();
        statementHolder.forEach(StatementBlock::updateState);
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        for (StatementBlock block : statementHolder) {
            block.prepareBuild(buildInfo);
        }
    }

    public String generateChildrenJava() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (StatementBlock block : statementHolder) {
            joiner.add(block.generateJava());
        }
        return joiner.toString();
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        for (StatementBlock block : statementHolder) {
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
                    StatementBlock block = BlockRegistry.getStatement(statementJson.optString("uid")).newBlock(statementJson);
                    statementHolder.addLast(block).execute();
                }
            }
        }
    }

    public StatementHolder getStatementHolder() {
        return statementHolder;
    }

    public static class Factory extends BlockFactory<PluginComponentBlock> {

        public Factory(Class<?> clazz) {
            super(clazz);
        }

        @Override
        protected PluginComponentBlock createUnknown() {
            return new Unknown();
        }
    }

    @BlockDefinition(uid = "unknown-plugin-component", name = "Unknown Plugin Component")
    public static class Unknown extends PluginComponentBlock {

        protected static final Factory factory = new Factory(Unknown.class);
        private JSONObject json;

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
