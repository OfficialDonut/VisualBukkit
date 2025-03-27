package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "stat-advanced-number-loop", name = "Advanced Number Loop", description = "Loops through a range of numbers")
public class StatAdvancedNumberLoop extends ContainerBlock {

    public StatAdvancedNumberLoop() {
        addParameter("Start", new ExpressionParameter(ClassInfo.of(int.class)));
        addParameter("End", new ExpressionParameter(ClassInfo.of(int.class)));
        addParameter("Update Value", new ExpressionParameter(ClassInfo.of(int.class)));
        addParameter("Update Type", new ChoiceParameter("+", "-", "*", "/", "%"));
        addParameter("Comparison", new ChoiceParameter("<", "<=", ">", ">=", "==", "!="));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        String loopVar = "loopNumber" + getNestedContainers(StatNumberLoop.class, StatAdvancedNumberLoop.class);
        return "for (int %1$s = %2$s; %1$s %3$s %4$s; %1$s %5$s= %6$s) { int $FINAL_%1$s = %1$s; %7$s }".formatted(loopVar, arg(0, buildInfo), arg(4, buildInfo), arg(1, buildInfo), arg(3, buildInfo), arg(2, buildInfo), generateChildrenJava(buildInfo));
    }
}
