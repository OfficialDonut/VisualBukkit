package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.PreparedStatement;

@Name("Execute SQL")
@Description("Executes an SQL statement")
@Category(StatementCategory.IO)
public class StatExecuteSQL extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("execute", PreparedStatement.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateParent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".execute();";
    }
}
