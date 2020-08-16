package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.PreparedStatement;

@Name("Set SQL Parameter")
@Description("Sets a parameter in a SQL statement")
@Category(StatementCategory.IO)
public class StatSetSQLParameter extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("set SQL parameter", Syntax.LINE_SEPARATOR,
                "type:     ", new ChoiceParameter("boolean", "double", "integer", "object", "varchar"), Syntax.LINE_SEPARATOR,
                "statement:", PreparedStatement.class, Syntax.LINE_SEPARATOR,
                "parameter:", int.class, Syntax.LINE_SEPARATOR,
                "value:    ", Object.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateParent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        String type = arg(0).toUpperCase();
        if (type.equals("OBJECT")) {
            type = "JAVA_OBJECT";
        }
        return arg(1) + ".setObject(" + arg(2) + "," + arg(3) + ",JDBCType." + type + ");";
    }
}
