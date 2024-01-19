package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.apache.commons.lang3.RandomStringUtils;

@BlockDefinition(id = "comp-event-listener", name = "Event Listener")
public class CompEventListener extends PluginComponentBlock {

    private final ClassParameter eventParameter = new ClassParameter(c -> !c.getMethods(m -> m.getName().equals("getHandlerList")).isEmpty());

    public CompEventListener() {
        addParameter("Event", eventParameter);
        addParameter("Priority", new ChoiceParameter("NORMAL", "HIGH", "HIGHEST", "LOW", "LOWEST", "MONITOR"));
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        buildInfo.getMainClass().addMethod(
                "@EventHandler(priority=EventPriority." + arg(1, buildInfo) + ")\n" +
                "public void $event_" + RandomStringUtils.randomAlphabetic(16) + "(" + getEvent().getName() + " event) throws Exception {" +
                generateChildrenJava(buildInfo) +
                "}");
    }

    public ClassInfo getEvent() {
        return eventParameter.getValue() != null ? eventParameter.getValue() : ClassInfo.of("org.bukkit.event.Event");
    }
}
