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

        ChoiceParameter parameter = new ChoiceParameter("true", "false");
        block.getParameters().add(parameter);
        block.getSyntaxBox().getChildren().set(0, parameter);
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }
}
