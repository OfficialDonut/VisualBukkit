package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.project.BuildInfo;

@BlockDefinition(id = "comp-consumer", name = "Consumer")
public class CompConsumer extends PluginComponentBlock {

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        buildInfo.getMainClass().addImport("java.util.function.Consumer");
        buildInfo.getMainClass().addField("public static final Consumer " + getConsumerField(getPluginComponent().getName() + " = (Consumer<Object>) consumerInput -> {" + generateChildrenJava(buildInfo) + "};"));
    }

    protected static String getConsumerField(String str) {
        return "$CONSUMER_" + str;
    }
}
