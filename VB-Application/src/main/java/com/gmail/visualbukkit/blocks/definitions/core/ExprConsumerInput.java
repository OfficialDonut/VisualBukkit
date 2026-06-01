package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-consumer-input", name = "Consumer Input", description = "The input to a consumer (must be used in a 'Consumer' plugin component)")
public class ExprConsumerInput extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompConsumer.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(VisualBukkitApp.javadocsURI("java/util/function/Consumer.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "consumerInput";
    }
}
