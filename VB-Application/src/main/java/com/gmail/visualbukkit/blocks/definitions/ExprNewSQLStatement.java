package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class ExprNewSQLStatement extends Expression {

    public ExprNewSQLStatement() {
        super("expr-new-sql-statement", "New Statement", "SQL", "A SQL statement");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(PreparedStatement.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("SQL", ClassInfo.STRING)) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return "connection.prepareStatement(" + arg(0) + ")";
            }
        };
    }
}
