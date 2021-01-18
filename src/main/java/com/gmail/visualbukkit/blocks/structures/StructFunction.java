package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@Description("Defines a function")
public class StructFunction extends StructureBlock {

    public StructFunction() {
        init("function ", new StringLiteralParameter());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> functionMethod = context.getMainClass().getMethod("function", String.class, List.class);
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                "Object localVariableScope = new Object();" +
                getChildJava() +
                "}" +
                functionMethod.getBody());
    }
}
