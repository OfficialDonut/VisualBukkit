package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import javafx.beans.binding.Bindings;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompGUIClickHandler extends PluginComponent {

    public CompGUIClickHandler() {
        super("comp-gui-click-handler", "GUI Click Handler", "GUI", "Handles GUI clicks");
    }

    @Override
    public Block createBlock() {
        StringLiteralParameter nameParameter = new StringLiteralParameter("GUI ID");
        Block block = new Block(this, nameParameter) {
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

        block.getTab().textProperty().bind(Bindings
                .when(nameParameter.getControl().textProperty().isNotEmpty())
                .then(Bindings.concat("GUI Click Handler - ", nameParameter.getControl().textProperty()))
                .otherwise("GUI Click Handler"));

        return block;
    }
}
