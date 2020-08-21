package us.donut.visualbukkit.blocks.structures;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

import java.util.List;

@Description("Defines a procedure")
public class StructProcedure extends StructureBlock {

    @Override
    protected Syntax init() {
        return new Syntax("procedure", new StringLiteralParameter());
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        MethodSource<JavaClassSource> procedureMethod = mainClass.getMethod("procedure", String.class, List.class);
        String childJava = getChildJava();
        procedureMethod.setBody(
                "if (procedure.equalsIgnoreCase(" + arg(0) + ")) {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "}" +
                procedureMethod.getBody());
    }
}
