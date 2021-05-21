package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import javafx.scene.Node;
import org.json.JSONObject;

public abstract class SimpleExpression extends Expression {

    public SimpleExpression(String id) {
        super(id);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Expression.Block {

        public Block(SimpleExpression expression, BlockParameter parameter) {
            super(expression);
            getBody().getStyleClass().clear();
            getHeader().getStyleClass().clear();
            getHeader().getChildren().clear();
            addToHeader((Node) parameter);
        }
    }
}
