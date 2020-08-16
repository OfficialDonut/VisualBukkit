package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A section in a config", "Returns: config"})
public class ExprConfigSection extends ExpressionBlock<ConfigurationSection> {

    @Override
    protected Syntax init() {
        return new Syntax("section", String.class, "of", ConfigurationSection.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getConfigurationSection(" + arg(0) + ")";
    }
}
