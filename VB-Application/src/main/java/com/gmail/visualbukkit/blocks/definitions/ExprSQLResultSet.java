package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExprSQLResultSet extends Expression {

    public ExprSQLResultSet() {
        super("expr-sql-result-set");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(ResultSet.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(PreparedStatement.class))) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-open-database-connection");
            }

            @Override
            public String toJava() {
                return arg(0) + ".executeQuery()";
            }
        };
    }
}
