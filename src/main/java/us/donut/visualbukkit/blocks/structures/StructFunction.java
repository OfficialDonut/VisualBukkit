package us.donut.visualbukkit.blocks.structures;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

import java.util.Collection;

@Description("Defines a function")
public class StructFunction extends StructureBlock {

    @Override
    protected Syntax init() {
        return new Syntax("function", new StringLiteralParameter());
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        MethodSource<JavaClassSource> functionMethod = mainClass.getMethod("function", String.class, Collection.class);
        String childJava = getChildJava();
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "}" +
                functionMethod.getBody());
    }
}
