package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.sql.PreparedStatement;

@Name("Set SQL Parameter")
@Description("Sets a parameter in a SQL statement")
public class StatSetSQLParameter extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("set SQL parameter")
                .line("type:     ", new ChoiceParameter("boolean", "double", "integer", "object", "varchar"))
                .line("statement:", PreparedStatement.class)
                .line("parameter:", int.class)
                .line("value:    ", Object.class);
    }

    @Override
    public String toJava() {
        if (isChildOf(StatOpenDatabaseConnection.class)) {
            String type = arg(0).toUpperCase();
            if (type.equals("OBJECT")) {
                type = "JAVA_OBJECT";
            }
            return arg(1) + ".setObject(" + arg(2) + "," + arg(3) + ",JDBCType." + type + ");";
        }
        throw new IllegalStateException();
    }
}
