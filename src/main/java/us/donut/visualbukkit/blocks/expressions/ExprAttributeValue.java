package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The value of an attribute", "Changers: set, add, remove", "Returns: number"})
public class ExprAttributeValue extends ChangeableExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Attribute.class, "value for", Attributable.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getAttribute(" + arg(0) + ").getBaseValue()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return arg(1) + ".getAttribute(" + arg(0) + ").setBaseValue(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
