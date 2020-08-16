package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The value of an attribute", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprAttributeValue extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax(Attribute.class, "value for", Attributable.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getAttribute(" + arg(0) + ").getBaseValue()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return arg(1) + ".getAttribute(" + arg(0) + ").setBaseValue(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
