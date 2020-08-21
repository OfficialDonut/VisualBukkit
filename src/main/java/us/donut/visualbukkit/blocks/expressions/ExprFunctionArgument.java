package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.structures.StructFunction;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An argument of a function", "Returns: object"})
public class ExprFunctionArgument extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("argument", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure(StructFunction.class);
    }

    @Override
    public String toJava() {
        return "args.get(" + arg(0) + ")";
    }
}
