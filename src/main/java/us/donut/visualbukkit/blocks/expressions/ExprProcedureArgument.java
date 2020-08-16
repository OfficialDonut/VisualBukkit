package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.structures.StructProcedure;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An argument of a procedure", "Returns: object"})
public class ExprProcedureArgument extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("argument", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure(StructProcedure.class);
    }

    @Override
    public String toJava() {
        return "args[" + arg(0) + "]";
    }
}
