package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import javafx.scene.Node;
import javafx.scene.Parent;

public class StatNumberLoop extends Container {

    public StatNumberLoop() {
        super("stat-number-loop");
    }

    @Override
    public Statement.Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public String toJava() {
                String loopVar = "loopNumber" + (getNestedLoops(this) + 1);
                return "for (int " + loopVar +  " = 0; " + loopVar + " < " + arg(0) + "; " + loopVar + "++) {" +
                        "Object FINAL_" + loopVar + " = " + loopVar + ";" +
                        getChildJava() +
                        "}";
            }
        };
    }

    public static int getNestedLoops(Node node) {
        int loops = 0;
        Parent parent = node.getParent();
        while (parent != null) {
            if (parent instanceof Container.ChildConnector) {
                if ((((Container.ChildConnector) parent).getOwner().getDefinition().getID().equals("stat-number-loop"))) {
                    loops++;
                }
            }
            parent = parent.getParent();
        }
        return loops;
    }
}
