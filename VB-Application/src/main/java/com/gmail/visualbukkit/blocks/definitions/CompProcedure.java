package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

public class CompProcedure extends PluginComponent {

    public CompProcedure() {
        super("comp-procedure");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter()) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> procedureMethod = buildContext.getMainClass().getMethod("procedure", String.class, List.class);
                procedureMethod.setBody(
                        "if (procedure.equalsIgnoreCase(" + arg(0) + ")) {" +
                        buildContext.getLocalVariableDeclarations() +
                        getChildJava() +
                        "return;" +
                        "}" +
                        procedureMethod.getBody());
            }
        };
    }
}
