package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Arbitrary java code")
public class StatJavaCode extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("java", new InputParameter());
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
