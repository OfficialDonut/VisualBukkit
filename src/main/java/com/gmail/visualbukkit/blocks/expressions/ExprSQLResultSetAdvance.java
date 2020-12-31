package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.statements.StatOpenDatabaseConnection;

import java.sql.ResultSet;

@Name("SQL Result Set Advance")
@Description("Advances the cursor of a result set and returns whether there are more rows")
public class ExprSQLResultSetAdvance extends ExpressionBlock<Boolean> {

    public ExprSQLResultSetAdvance() {
        init("advance ", ResultSet.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("SQL result set advance must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".next()";
    }
}
