package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatAdvancedNumberLoop extends Container {

    public StatAdvancedNumberLoop() {
        super("stat-advanced-number-loop", "Advanced Number Loop", "VB", "Loops through a range of numbers");
    }

    @Override
    public Block createBlock() {
        return new Container.Block(this, new ExpressionParameter("Start", ClassInfo.INT), new ExpressionParameter("Stop", ClassInfo.INT), new ExpressionParameter("Increment", ClassInfo.INT)) {
            @Override
            public String toJava() {
                String loopVar = "loopNumber" + (StatNumberLoop.getNestedLoops(this) + 1);
                return "for (int " + loopVar +  " = " + arg(0) + "; " + loopVar + " < " + arg(1) + "; " + loopVar + " += " + arg(2) + ") {" +
                        "int FINAL_" + loopVar + " = " + loopVar + ";" +
                        getChildJava() +
                        "}";
            }
        };
    }
}
