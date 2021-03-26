package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class ExprNewSQLStatement extends Expression {

    public ExprNewSQLStatement() {
        super("expr-new-sql-statement", PreparedStatement.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(String.class)) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return "connection.prepareStatement(" + arg(0) + ")";
            }
        };
    }
}
