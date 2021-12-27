package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.sql.ResultSet;

public class ExprAdvanceResultSetCursor extends Expression {

    public ExprAdvanceResultSetCursor() {
        super("expr-advance-result-set-cursor", "Advance Result Set Cursor", "SQL", "Moves the result set cursor forward one row, returns false if there are no more rows");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Result Set", ClassInfo.of(ResultSet.class))) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatOpenDatabaseConnection.class);
            }

            @Override
            public String toJava() {
                return arg(0) + ".next()";
            }
        };
    }
}
