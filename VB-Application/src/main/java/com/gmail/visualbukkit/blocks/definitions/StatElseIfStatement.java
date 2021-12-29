package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatElseIfStatement extends Container {

    public StatElseIfStatement() {
        super("stat-else-if-statement", "Else If Statement", "VB", "Runs code if the condition of the previous if statement is false");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Condition", ClassInfo.BOOLEAN), new ChoiceParameter("Mode", "normal", "negate condition")) {
            @Override
            public void update() {
                super.update();
                setValid(checkForPrevious(StatIfStatement.class) || checkForPrevious(StatElseIfStatement.class));
            }

            @Override
            public String toJava() {
                return arg(1).equals("normal") ?
                        ("else if (" + arg(0) + ") {" + getChildJava() + "}") :
                        ("else if (!" + arg(0) + ") {" + getChildJava() + "}");
            }
        };
    }
}
