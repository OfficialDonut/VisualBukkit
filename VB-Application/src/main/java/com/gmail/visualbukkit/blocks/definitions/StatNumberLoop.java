package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import javafx.scene.Node;
import javafx.scene.Parent;

public class StatNumberLoop extends Container {

    public StatNumberLoop() {
        super("stat-number-loop", "Number Loop", "VB", "Loops a certain number of times");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Number", ClassInfo.INT)) {
            @Override
            public String toJava() {
                String loopVar = "loopNumber" + (getNestedLoops(this) + 1);
                return "for (int " + loopVar +  " = 0; " + loopVar + " < " + arg(0) + "; " + loopVar + "++) {" +
                        "double FINAL_" + loopVar + " = " + loopVar + ";" +
                        getChildJava() +
                        "}";
            }
        };
    }

    protected static int getNestedLoops(Node node) {
        int loops = 0;
        Parent parent = node.getParent();
        while (parent != null) {
            if (parent instanceof Container.Block b && (b.getDefinition().getClass() == StatNumberLoop.class || b.getDefinition().getClass() == StatAdvancedNumberLoop.class)) {
                loops++;
            }
            parent = parent.getParent();
        }
        return loops;
    }
}
