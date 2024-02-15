package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.CheckBoxParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@BlockDefinition(id = "comp-gui", name = "GUI")
public class CompGUI extends PluginComponentBlock {

    private final CheckBoxParameter modeParameter = new CheckBoxParameter("Recreate when opened");

    public CompGUI() {
        addParameter("Title", new ExpressionParameter(ClassInfo.of(String.class)));
        addParameter("Size", new ExpressionParameter(ClassInfo.of(int.class)));
        addParameter("Mode", modeParameter);
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        String id = "\"" + getPluginComponent().getName() + "\"";
        MethodSource<JavaClassSource> enableMethod = buildInfo.getMainClass().getMethod("onEnable");
        enableMethod.setBody(enableMethod.getBody() +
                "GUIManager.getInstance().register(" + id + "," + modeParameter.isSelected() + ", guiPlayer -> {" +
                "try {" +
                "org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier(" + id + ")," + arg(1, buildInfo) + "," + arg(0,  buildInfo) + ");" +
                generateChildrenJava(buildInfo) +
                "return guiInventory;" +
                "} catch (Exception e) { e.printStackTrace(); return null; }});");
    }
}
