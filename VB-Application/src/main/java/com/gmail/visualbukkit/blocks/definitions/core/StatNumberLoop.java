package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import javafx.scene.Node;
import javafx.scene.Parent;

@BlockDefinition(id = "stat-number-loop", name = "Number Loop", description = "Loops a certain number of times")
public class StatNumberLoop extends ContainerBlock {

    public StatNumberLoop() {
        addParameter("Number", new ExpressionParameter(ClassInfo.of(int.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        String loopVar = "loopNumber" + (getNestedLoops(this) + 1);
        return "for (int %1$s = 0; %1$s < %2$s; %1$s++) { int $FINAL_%1$s = %1$s; %3$s }".formatted(loopVar, arg(0, buildInfo), generateChildrenJava(buildInfo));
    }

    protected static int getNestedLoops(Node node) {
        int loops = 0;
        Parent parent = node.getParent();
        while (parent != null) {
            if (parent instanceof StatNumberLoop || parent instanceof StatAdvancedNumberLoop) {
                loops++;
            }
            parent = parent.getParent();
        }
        return loops;
    }
}
