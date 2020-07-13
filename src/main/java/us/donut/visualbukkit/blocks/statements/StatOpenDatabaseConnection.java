package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Opens a connection with a database")
public class StatOpenDatabaseConnection extends ParentBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("open database connection");
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.DATABASE);
        String childJava = getChildJava();
        if (childJava.isEmpty()) {
            childJava = "int foo = 0;";
        }
        return "Connection connection = DatabaseManager.getConnection();" +
                "try {" + childJava + "}" +
                "finally { connection.close(); }";
    }
}
