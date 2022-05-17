package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatAdvancedNumberLoop extends Container {

    public StatAdvancedNumberLoop() {
        super("stat-advanced-number-loop", "Advanced Number Loop", "VB", "Loops through a range of numbers");
    }

    @Override
    public Block createBlock() {
        return new Container.Block(this, new ExpressionParameter("Start", ClassInfo.DOUBLE), new ChoiceParameter("Mode", "<", "<=", ">", ">="), new ExpressionParameter("Stop", ClassInfo.DOUBLE), new ExpressionParameter("Increment", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                String loopVar = "loopNumber" + (StatNumberLoop.getNestedLoops(this) + 1);
                return "for (double " + loopVar + " = " + arg(0) + "; " + loopVar + " " + arg(1) + " " + arg(2) + "; " + loopVar + " += " + arg(3) + ") {" +
                        "double FINAL_" + loopVar + " = " + loopVar + ";" +
                        getChildJava() +
                        "}";
            }
        };
    }
}
