package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class StatSetSQLParameter extends Statement {

    public StatSetSQLParameter() {
        super("stat-set-sql-parameter");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(PreparedStatement.class)), new ExpressionParameter(ClassInfo.INT), new ExpressionParameter(ClassInfo.OBJECT)) {
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
