package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.sql.ResultSet;

@Description({"Advances the cursor of a result set and returns whether there are more rows", "Returns: boolean"})
public class ExprAdvanceCursor extends ExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("advance cursor of", ResultSet.class);
    }

    @Override
    public String toJava() {
        if (isChildOf(StatOpenDatabaseConnection.class)) {
            return arg(0) + ".next()";
        }
        throw new IllegalStateException();
    }
}
