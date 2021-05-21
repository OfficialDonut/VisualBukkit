package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExprSerializedItem extends Expression {

    public ExprSerializedItem() {
        super("expr-serialized-item");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(null);
    }

    public Block createBlock(String itemString) {
        return new Block(itemString);
    }

    public class Block extends Expression.Block {

        private String itemString;

        public Block(String itemString) {
            super(ExprSerializedItem.this);
            this.itemString = itemString;
        }

        @Override
        public String toJava() {
            return null;
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            json.append("parameters", itemString);
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            JSONArray parameterArray = json.optJSONArray("parameters");
            if (parameterArray != null && parameterArray.length() > 0) {
                itemString = parameterArray.optString(0);
            }
        }
    }
}
