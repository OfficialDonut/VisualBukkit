package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.Statement;

@Name("SQL Statement")
@Description({"An SQL statement", "Returns: SQL statement"})
public class ExprSQLStatement extends ExpressionBlock<Statement> {

    @Override
    protected Syntax init() {
        return new Syntax("SQL", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return "connection.prepareStatement(" + arg(0) + ")";
    }
}
