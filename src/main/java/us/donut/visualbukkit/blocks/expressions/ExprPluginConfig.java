package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.file.FileConfiguration;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The plugin config", "Returns: config"})
public class ExprPluginConfig extends ExpressionBlock<FileConfiguration> {

    @Override
    protected Syntax init() {
        return new Syntax("plugin config");
    }

    @Override
    public String toJava() {
        return "PluginMain.getInstance().getConfig()";
    }
}
