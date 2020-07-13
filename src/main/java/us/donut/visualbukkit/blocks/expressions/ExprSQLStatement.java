package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.sql.Statement;

@Name("SQL Statement")
@Description({"An SQL statement", "Returns: SQL statement"})
public class ExprSQLStatement extends ExpressionBlock<Statement> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("SQL", String.class);
    }

    @Override
    public String toJava() {
        if (isChildOf(StatOpenDatabaseConnection.class)) {
            return "connection.prepareStatement(" + arg(0) + ")";
        }
        throw new IllegalStateException();
    }
}
