package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Initializes database connections")
@Category(StatementCategory.IO)
public class StatInitializeDatabase extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("initialize database connections", Syntax.LINE_SEPARATOR,
                "url:     ", String.class, Syntax.LINE_SEPARATOR,
                "username:", String.class, Syntax.LINE_SEPARATOR,
                "password:", String.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.DATABASE);
        return "DatabaseManager.init(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
