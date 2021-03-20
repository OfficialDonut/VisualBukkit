package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.List;

public class StatListLoop extends Container {

    public StatListLoop() {
        super("stat-list-loop");
    }

    @Override
    public Block createBlock() {
        return new Container.Block(this, new ExpressionParameter(List.class)) {
            @Override
            public String toJava() {
                return "for (Object FINAL_loopValue" + (getNestedLoops(this) + 1) + " : " + arg(0) + ") {" + getChildJava() + "}";
            }
        };
    }

    public static int getNestedLoops(Node node) {
        int loops = 0;
        Parent parent = node.getParent();
        while (parent != null) {
            if (parent instanceof Container.ChildConnector) {
                if ((((Container.ChildConnector) parent).getOwner().getDefinition().getID().equals("stat-list-loop"))) {
                    loops++;
                }
            }
            parent = parent.getParent();
        }
        return loops;
    }
}
