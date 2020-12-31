package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;

import java.sql.PreparedStatement;

@Name("Execute SQL Statement")
@Description("Executes an SQL statement")
public class StatExecuteSQLStatement extends StatementBlock {

    public StatExecuteSQLStatement() {
        init("execute ", PreparedStatement.class);
    }

    @Override
    public void update() {
        super.update();
        validateParent("Execute SQL statement must be used in open database connection", StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".execute();";
    }
}
