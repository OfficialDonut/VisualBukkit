package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.function.Consumer;

@BlockDefinition(id = "expr-consumer", name = "Consumer", description = "A consumer")
public class ExprConsumer extends ExpressionBlock {

    private final PluginComponentParameter parameter = new PluginComponentParameter(CompConsumer.class);

    public ExprConsumer() {
        addParameter("Consumer", parameter);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Consumer.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Consumer.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "PluginMain." + CompConsumer.getConsumerField(parameter.getValue());
    }
}
