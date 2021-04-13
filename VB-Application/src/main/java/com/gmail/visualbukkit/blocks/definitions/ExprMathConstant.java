package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;

public class ExprMathConstant extends Expression {

    public ExprMathConstant() {
        super("expr-math-constant", ClassInfo.DOUBLE);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                return "Math." + arg(0).toUpperCase();
            }
        };

        ChoiceParameter parameter = new ChoiceParameter("pi", "e");
        block.getParameters().add(parameter);
        block.getSyntaxBox().getChildren().set(0, parameter);
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }
}
