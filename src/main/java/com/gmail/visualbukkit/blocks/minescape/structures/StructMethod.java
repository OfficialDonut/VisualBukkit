package com.gmail.visualbukkit.blocks.minescape.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@Category(Category.MINESCAPE)
@Description("Defines a Method")
public class StructMethod extends StructureBlock {

    public StructMethod() {
        init("Method: ", new StringLiteralParameter());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> functionMethod = context.getMainClass().getMethod("function", String.class, List.class);
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                getChildJava() +
                "}" +
                functionMethod.getBody());
    }
}
