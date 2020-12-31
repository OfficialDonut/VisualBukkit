package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.statements.StatOpenDatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Name("SQL Result Set")
@Description("The result set of an SQL query")
public class ExprSQLResultSet extends ExpressionBlock<ResultSet> {

    public ExprSQLResultSet() {
        init("result set of ", PreparedStatement.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("SQL result set must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".executeQuery()";
    }
}
