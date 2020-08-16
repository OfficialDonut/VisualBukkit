package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Opens a connection with a database")
@Category(StatementCategory.IO)
public class StatOpenDatabaseConnection extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("open database connection");
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.DATABASE);
        return "Connection connection = DatabaseManager.getConnection();" +
                "try {" + getChildJava() + "}" +
                "finally { connection.close(); }";
    }
}
