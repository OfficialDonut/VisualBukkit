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
                String str = arg(0);
                return str.contains("&") ? "ChatColor.translateAlternateColorCodes('&'," + str + ")" : str;
            }
        };

        block.getHeaderBox().getChildren().clear();
        block.addToHeader(new StringLiteralParameter());
        block.getHeaderBox().getStyleClass().clear();
        block.getSyntaxBox().getStyleClass().clear();

        return block;
    }
}
