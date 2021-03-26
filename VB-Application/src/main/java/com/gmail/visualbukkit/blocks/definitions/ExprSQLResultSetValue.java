package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.ResultSet;

public class ExprSQLResultSetValue extends Expression {

    public ExprSQLResultSetValue() {
        super("expr-sql-result-set-value", Object.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ResultSet.class), new ExpressionParameter(String.class)) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return arg(0) + ".getObject(" + arg(1) + ")";
            }
        };
    }
}
