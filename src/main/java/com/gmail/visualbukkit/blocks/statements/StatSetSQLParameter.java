package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;

import java.sql.PreparedStatement;

@Name("Set SQL Parameter")
@Description("Sets a parameter in a SQL statement")
public class StatSetSQLParameter extends StatementBlock {

    public StatSetSQLParameter() {
        init("set SQL parameter");
        initLine("statement: ", PreparedStatement.class);
        initLine("parameter: ", int.class);
        initLine("value:     ", Object.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("Set SQL parameter must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setObject(" + arg(1) + "," + arg(2) + ");";
    }
}
