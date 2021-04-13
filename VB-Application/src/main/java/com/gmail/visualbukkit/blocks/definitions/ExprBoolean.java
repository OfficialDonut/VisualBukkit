package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;

public class ExprBoolean extends Expression {

    public ExprBoolean() {
        super("expr-boolean", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };

        block.getHeaderBox().getChildren().clear();
        block.addToHeader(new ChoiceParameter("true", "false"));
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }
}
