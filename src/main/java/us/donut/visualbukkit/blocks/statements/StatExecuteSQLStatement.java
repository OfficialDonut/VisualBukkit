package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.sql.PreparedStatement;

@Name("Execute SQL Statement")
@Description("Executes an SQL statement")
public class StatExecuteSQLStatement extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", PreparedStatement.class);
    }

    @Override
    public String toJava() {
        if (isChildOf(StatOpenDatabaseConnection.class)) {
            return arg(0) + ".execute();";
        }
        throw new IllegalStateException();
    }
}
