package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.structures.StructProcedure;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The arguments of a procedure", "Returns: list of objects"})
public class ExprProcedureArguments extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("arguments");
    }

    @Override
    public void update() {
        super.update();
        validateStructure(StructProcedure.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(args)";
    }
}
