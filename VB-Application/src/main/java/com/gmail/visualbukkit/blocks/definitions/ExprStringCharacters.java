package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprStringCharacters extends Expression {

    public ExprStringCharacters() {
        super("expr-string-characters");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LIST;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "PluginMain.createList(" + arg(0) + ".toCharArray())";
            }
        };
    }
}
