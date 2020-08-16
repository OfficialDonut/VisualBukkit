package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.structures.StructFunction;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Returns a value from a function")
public class StatFunctionReturn extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("return", Object.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateStructure(StructFunction.class);
    }

    @Override
    public String toJava() {
        return "return " + arg(0) + ";";
    }
}
