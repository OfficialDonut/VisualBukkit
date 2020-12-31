package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.statements.StatOpenDatabaseConnection;

import java.sql.ResultSet;

@Name("SQL Result Set Value")
@Description("The value in a column of a result set")
public class ExprSQLResultSetValue extends ExpressionBlock<Object> {

    public ExprSQLResultSetValue() {
        init("column ", String.class, " of ", ResultSet.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("SQL result set value must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getObject(" + arg(0) + ")";
    }
}
