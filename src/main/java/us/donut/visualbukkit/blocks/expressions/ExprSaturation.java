package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The saturation level of a player", "Returns: number"})
public class ExprSaturation extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("saturation of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSaturation()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return arg(0) + ".setSaturation(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }

    @Override
    public Class<?> getReturnType() {
        return float.class;
    }
}
