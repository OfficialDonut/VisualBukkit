package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.ResultSet;

public class ExprAdvanceSQLResultSetCursor extends Expression {

    public ExprAdvanceSQLResultSetCursor() {
        super("expr-advance-sql-result-set-cursor", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(ResultSet.class))) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return arg(0) + ".next()";
            }
        };
    }
}
