package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-list-loop", name = "List Loop", description = "Loops through each value in a list")
public class StatListLoop extends ContainerBlock {

    public StatListLoop() {
        addParameter("List", new ExpressionParameter(ClassInfo.of(Iterable.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "for (Object $FINAL_loopValue" + getNestedContainers(StatListLoop.class) + " : " + arg(0, buildInfo) + ") {" + generateChildrenJava(buildInfo) + "}";
    }
}
