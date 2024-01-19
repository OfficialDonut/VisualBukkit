package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import org.apache.commons.lang3.RandomStringUtils;

@BlockDefinition(id = "comp-gui-click-handler", name = "GUI Click Handler")
public class CompGUIClickHandler extends PluginComponentBlock {

    public CompGUIClickHandler() {
        addParameter("GUI", new PluginComponentParameter(CompGUI.class));
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        buildInfo.getMainClass().addMethod(
                "@EventHandler\n" +
                "public void $event_" + RandomStringUtils.randomAlphabetic(16) + "(GUIClickEvent guiClickEvent) throws Exception {" +
                "if (guiClickEvent.getID().equals(" + arg(0, buildInfo) + ")) {" +
                generateChildrenJava(buildInfo) +
                "}}");
    }
}
