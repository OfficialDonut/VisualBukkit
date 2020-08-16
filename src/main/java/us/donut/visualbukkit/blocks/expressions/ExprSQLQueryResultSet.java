package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Name("SQL Query Result Set")
@Description({"The result set of an SQL query", "Returns: result set"})
public class ExprSQLQueryResultSet extends ExpressionBlock<ResultSet> {

    @Override
    protected Syntax init() {
        return new Syntax("result set of", PreparedStatement.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".executeQuery()";
    }
}
