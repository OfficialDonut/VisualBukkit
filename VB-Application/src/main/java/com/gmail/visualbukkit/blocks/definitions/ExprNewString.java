package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class ExprNewString extends Expression {

    public ExprNewString() {
        super("expr-new-string", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                return "ChatColor.translateAlternateColorCodes('&'," + arg(0) + ")";
            }
        };

        StringLiteralParameter parameter = new StringLiteralParameter();
        block.getParameters().add(parameter);
        block.getSyntaxBox().getChildren().set(0, parameter);
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }
}
