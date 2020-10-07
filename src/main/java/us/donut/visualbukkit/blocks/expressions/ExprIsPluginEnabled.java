package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a plugin is enabled", "Returns: boolean"})
public class ExprIsPluginEnabled extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax("plugin named", String.class, new BinaryChoiceParameter("is", "is not"), "enabled");
    }

    @Override
    protected String toNonNegatedJava() {
        return "Bukkit.getPluginManager().isPluginEnabled(" + arg(0) + ")";
    }
}
