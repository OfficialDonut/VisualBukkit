package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.PluginComponent;
import com.gmail.visualbukkit.project.ProjectManager;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.io.IOException;

@BlockDefinition(id = "comp-tab-complete-handler", name = "Tab Complete Handler")
public class CompTabCompleteHandler extends PluginComponentBlock {

    private final PluginComponentParameter parameter = new PluginComponentParameter(CompCommand.class);

    public CompTabCompleteHandler() {
        addParameter("Command", parameter);
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        String command = "null";
        if (ProjectManager.current().getPluginComponent(parameter.getValue()) instanceof PluginComponent p) {
            try {
                if (p.load() instanceof CompCommand c) {
                    command = c.getName();
                }
            } catch (IOException e) {
                VisualBukkitApp.displayException(e);
            }
        }
        MethodSource<JavaClassSource> tabCompleteMethod = buildInfo.getMainClass().getMethod("onTabComplete", "CommandSender", "Command", "String", "String[]");
        tabCompleteMethod.setBody(
                "if (command.getName().equalsIgnoreCase(\"" + command + "\")) {" +
                "try {" +
                generateChildrenJava(buildInfo) +
                "} catch (Exception e) { e.printStackTrace(); }" +
                "return null;" +
                "}" +
                tabCompleteMethod.getBody());
    }
}
