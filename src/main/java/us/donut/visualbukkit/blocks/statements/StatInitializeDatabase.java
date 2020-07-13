package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Initializes database connections")
public class StatInitializeDatabase extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("initialize database connections")
                .line("url:     ", String.class)
                .line("username:", String.class)
                .line("password:", String.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.DATABASE);
        return "DatabaseManager.init(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
