package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class StatSetSQLParameter extends Statement {

    public StatSetSQLParameter() {
        super("stat-set-sql-parameter");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(PreparedStatement.class), new ExpressionParameter(int.class), new ExpressionParameter(Object.class)) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return arg(0) + ".setObject(" + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
