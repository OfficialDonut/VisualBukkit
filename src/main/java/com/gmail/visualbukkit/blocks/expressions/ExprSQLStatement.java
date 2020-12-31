package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.statements.StatOpenDatabaseConnection;

import java.sql.PreparedStatement;

@Name("SQL Statement")
@Description("An SQL statement")
public class ExprSQLStatement extends ExpressionBlock<PreparedStatement> {

    public ExprSQLStatement() {
        init("SQL ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("SQL statement must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return "connection.prepareStatement(" + arg(0) + ")";
    }
}
