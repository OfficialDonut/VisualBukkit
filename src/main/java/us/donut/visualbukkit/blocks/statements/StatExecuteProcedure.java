package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description("Executes a procedure")
public class StatExecuteProcedure extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("execute procedure", new StringLiteralParameter(), List.class);
    }

    @Override
    public String toJava() {
        return "procedure(" + arg(0) + "," + arg(1) + ");";
    }
}
