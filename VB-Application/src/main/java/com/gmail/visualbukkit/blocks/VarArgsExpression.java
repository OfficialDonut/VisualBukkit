package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.ui.IconButton;
import org.json.JSONObject;

import java.util.Arrays;

public abstract class VarArgsExpression extends Expression {

    public VarArgsExpression(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends Expression.Block {

        private int size = 0;

        public Block(VarArgsExpression expression, BlockParameter<?>... parameters) {
            super(expression, parameters);
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

        protected void push(BlockParameter<?> parameter) {
            parameters = Arrays.copyOf(parameters, parameters.length + 1);
            parameters[parameters.length - 1] = parameter;
            getBody().getChildren().add(parameter);
            adjustParameterLabels();
        }

        protected void pop() {
            BlockParameter<?> parameter = parameters[parameters.length - 1];
            parameters = Arrays.copyOf(parameters, parameters.length - 1);
            getBody().getChildren().remove(parameter);
            adjustParameterLabels();
        }

        private void adjustParameterLabels() {
            if (parameters.length > 0) {
                int maxLen = -1;
                for (BlockParameter<?> parameter : parameters) {
                    int len = parameter.getLabelText().length();
                    if (len > maxLen) {
                        maxLen = len;
                    }
                }
                for (BlockParameter<?> parameter : parameters) {
                    parameter.getLabel().setText(String.format("%-" + (maxLen + 1) + "s", parameter.getLabelText() + ":"));
                }
            }
        }

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
