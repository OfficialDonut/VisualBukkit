package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.IconButton;
import org.json.JSONObject;

public abstract class VarArgsExpression extends Expression {

    public VarArgsExpression(String id) {
        super(id);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Expression.Block {

        protected int size = 0;

        public Block(VarArgsExpression expression) {
            super(expression);
            addToHeader(new IconButton("plus", null, e -> {
                increaseSize();
                size++;
            }));
            addToHeader(new IconButton("minus", null, e -> {
                if (size > 0) {
                    decreaseSize();
                    size--;
                }
            }));
        }

        protected abstract void increaseSize();

        protected abstract void decreaseSize();

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            if (size != 0) {
                json.put("size", size);
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            for (int i = 0; i < json.optInt("size"); i++) {
                increaseSize();
                size++;
            }
            super.deserialize(json);
        }
    }
}
