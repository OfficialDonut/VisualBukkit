package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@Description("Defines a procedure")
public class StructProcedure extends StructureBlock {

    public StructProcedure() {
        init("procedure ", new StringLiteralParameter());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> procedureMethod = context.getMainClass().getMethod("procedure", String.class, List.class);
        procedureMethod.setBody(
                "if (procedure.equalsIgnoreCase(" + arg(0) + ")) {" +
                "Object localVariableScope = new Object();" +
                getChildJava() +
                "}" +
                procedureMethod.getBody());
    }
}
