package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatWhileLoop extends Container {

    public StatWhileLoop() {
        super("stat-while-loop");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.BOOLEAN), new ChoiceParameter("normal", "negate condition")) {
            @Override
            public String toJava() {
                return arg(1).equals("normal") ?
                        "while (" + arg(0) + ") {" + getChildJava() + "}" :
                        "while (!" + arg(0) + ") {" + getChildJava() + "}";
            }
        };
    }
}
