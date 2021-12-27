package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import javafx.beans.binding.Bindings;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

public class CompFunction extends PluginComponent {

    public CompFunction() {
        super("comp-function", "Function", "VB", "Defines a function");
    }

    @Override
    public Block createBlock() {
        StringLiteralParameter nameParameter = new StringLiteralParameter("Name");
        Block block = new Block(this, nameParameter) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> functionMethod = buildContext.getMainClass().getMethod("function", String.class, List.class);
                functionMethod.setBody(
                        "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                        buildContext.getLocalVariableDeclarations() +
                        getChildJava() +
                        "}" +
                        functionMethod.getBody());
            }
        };

        block.getTab().textProperty().bind(Bindings
                .when(nameParameter.getControl().textProperty().isNotEmpty())
                .then(Bindings.concat("Function - ", nameParameter.getControl().textProperty()))
                .otherwise("Function"));

        return block;
    }
}
