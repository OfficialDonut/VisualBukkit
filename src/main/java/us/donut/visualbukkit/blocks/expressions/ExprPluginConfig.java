package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.file.FileConfiguration;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The plugin config", "Returns: config"})
public class ExprPluginConfig extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("plugin config");
    }

    @Override
    public String toJava() {
        return "getConfig()";
    }

    @Override
    public Class<?> getReturnType() {
        return FileConfiguration.class;
    }
}
