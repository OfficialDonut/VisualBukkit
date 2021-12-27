package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.ResultSet;

public class ExprSQLResultSetValue extends Expression {

    public ExprSQLResultSetValue() {
        super("expr-sql-result-set-value", "Result Set Value", "SQL", "The value in a result set column");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("ResultSet", ClassInfo.of(ResultSet.class)), new ExpressionParameter("Column", ClassInfo.STRING)) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return arg(0) + ".getObject(" + arg(1) + ")";
            }
        };
    }
}
