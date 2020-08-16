package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.ResultSet;

@Description({"Advances the cursor of a result set and returns whether there are more rows", "Returns: boolean"})
public class ExprAdvanceCursor extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("advance cursor of", ResultSet.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".next()";
    }
}
