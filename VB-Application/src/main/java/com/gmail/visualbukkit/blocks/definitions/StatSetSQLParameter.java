package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class StatSetSQLParameter extends Statement {

    public StatSetSQLParameter() {
        super("stat-set-sql-parameter", "Set Statement Parameter", "SQL", "Sets a statement parameter (parameter numbers start at 1)");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Statement", ClassInfo.of(PreparedStatement.class)), new ExpressionParameter("Parameter", ClassInfo.INT), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return arg(0) + ".setObject(" + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
