package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompGUIClickHandler extends PluginComponent {

    public CompGUIClickHandler() {
        super("comp-gui-click-handler");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter()) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.GUI);
                MethodSource<JavaClassSource> eventMethod = buildContext.getMainClass().getMethod("onGUIClick", "GUIClickEvent");
                eventMethod.setBody(eventMethod.getBody() +
                        "if (event.getID().equalsIgnoreCase(" + arg(0) + ")) {" +
                        buildContext.getLocalVariableDeclarations() +
                        getChildJava() +
                        "return;" +
                        "}");
            }
        };
    }
}
