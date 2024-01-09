package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.apache.commons.lang3.RandomStringUtils;

@BlockDefinition(uid = "comp-event-listener", name = "Event Listener")
public class CompEventListener extends PluginComponentBlock {

    private final ClassParameter eventParameter = new ClassParameter(c -> !c.getMethods(m -> m.getName().equals("getHandlerList")).isEmpty());

    public CompEventListener() {
        addParameter("Event", eventParameter);
        addParameter("Priority", new ChoiceParameter("NORMAL", "HIGH", "HIGHEST", "LOW", "LOWEST", "MONITOR"));
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        buildInfo.getMainClass().addMethod(
                "@EventHandler(priority=EventPriority." + arg(1) + ")" +
                "public void $event_" + RandomStringUtils.randomAlphabetic(16) + "(" + getEvent().getName() + " event) throws Exception {" +
                buildInfo.getLocalVariableDeclarations() +
                generateChildrenJava() +
                "}");
    }

    public ClassInfo getEvent() {
        return eventParameter.getValue() != null ? eventParameter.getValue() : ClassInfo.of("org.bukkit.event.Event");
    }
}
