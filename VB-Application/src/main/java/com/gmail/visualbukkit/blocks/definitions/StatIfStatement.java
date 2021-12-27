package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatIfStatement extends Container {

    public StatIfStatement() {
        super("stat-if-statement", "If Statement", "VB", "Runs code if a condition is true");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Condition", ClassInfo.BOOLEAN), new ChoiceParameter("Mode", "normal", "negate condition")) {
            @Override
            public String toJava() {
                return arg(1).equals("normal") ?
                        ("if (" + arg(0) + ") {" + getChildJava() + "}") :
                        ("if (!" + arg(0) + ") {" + getChildJava() + "}");
            }
        };
    }
}
