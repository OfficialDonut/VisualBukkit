package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

public class CompFunction extends PluginComponent {

    public CompFunction() {
        super("comp-function");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter()) {
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
    }
}
