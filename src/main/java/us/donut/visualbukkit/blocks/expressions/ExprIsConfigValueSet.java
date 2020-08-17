package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.Configuration;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a config value is set", "Returns: boolean"})
public class ExprIsConfigValueSet extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new BinaryChoiceParameter("is", "is not"), "set in", Configuration.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(2) + ".isSet(" + arg(0) + ")";
    }
}
