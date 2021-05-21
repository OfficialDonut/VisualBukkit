package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import javafx.beans.binding.Bindings;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompCreateGUI extends PluginComponent {

    public CompCreateGUI() {
        super("comp-create-gui");
    }

    @Override
    public Block createBlock() {
        StringLiteralParameter nameParameter = new StringLiteralParameter();
        Block block = new Block(this, nameParameter, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.INT)) {
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
                        toJava() +
                        "return guiInventory;" +
                        "} catch (Exception e) { e.printStackTrace(); return null; }});");
            }
        };

        block.getTab().textProperty().bind(Bindings
                .when(nameParameter.textProperty().isNotEmpty())
                .then(Bindings.concat("Create GUI: ", nameParameter.textProperty()))
                .otherwise("Create GUI"));

        return block;
    }
}
