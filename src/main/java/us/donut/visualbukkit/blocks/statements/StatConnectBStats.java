package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Name("Connect bStats")
public class StatConnectBStats extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("connect bStats with plugin ID", int.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.BSTATS);
        return "new org.bstats.bukkit.Metrics(PluginMain.getInstance()," + arg(0) + ");";
    }
}
