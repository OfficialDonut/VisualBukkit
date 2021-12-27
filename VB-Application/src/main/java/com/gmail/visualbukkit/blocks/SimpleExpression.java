package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import org.json.JSONObject;

public abstract class SimpleExpression extends Expression {

    public SimpleExpression(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Expression.Block {

        public Block(SimpleExpression expression, BlockParameter<?> parameter) {
            super(expression, parameter);
            getStyleClass().clear();
            getChildren().setAll(parameter);
            parameter.getChildren().remove(parameter.getLabel());
        }
    }
}
