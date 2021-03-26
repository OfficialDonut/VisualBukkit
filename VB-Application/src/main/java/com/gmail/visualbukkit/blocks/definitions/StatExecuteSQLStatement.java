package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class StatExecuteSQLStatement extends Statement {

    public StatExecuteSQLStatement() {
        super("stat-execute-sql-statement");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(PreparedStatement.class)) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return arg(0) + ".execute();";
            }
        };
    }
}
