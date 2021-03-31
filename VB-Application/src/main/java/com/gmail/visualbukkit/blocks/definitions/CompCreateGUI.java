package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompCreateGUI extends PluginComponent {

    public CompCreateGUI() {
        super("comp-create-gui");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter(), new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.GUI);
                MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
                enableMethod.setBody(enableMethod.getBody() +
                        "GUIManager.getInstance().register(" + arg(0) + ", guiPlayer -> {" +
                        "try {" +
                        buildContext.getLocalVariableDeclarations() +
                        "org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier(" + arg(0) + ")," + arg(2) + "," + arg(1) + ");" +
                        getChildJava() +
                        "return guiInventory;" +
                        "} catch (Exception e) { e.printStackTrace(); return null; }});");
            }
        };
    }
}
