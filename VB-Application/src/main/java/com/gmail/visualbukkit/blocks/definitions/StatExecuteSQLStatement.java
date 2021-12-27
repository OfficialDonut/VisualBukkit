package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;

public class StatExecuteSQLStatement extends Statement {

    public StatExecuteSQLStatement() {
        super("stat-execute-sql-statement", "Execute Statement", "SQL", "Executes a SQL statement");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Statement", ClassInfo.of(PreparedStatement.class))) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return arg(0) + ".execute();";
            }
        };
    }
}
