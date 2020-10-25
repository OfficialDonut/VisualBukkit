package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The version of a plugin", "Returns: string"})
public class ExprPluginVersion extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("version of plugin", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getPluginManager().getPlugin(" + arg(0) + ").getDescription().getVersion()";
    }
}
