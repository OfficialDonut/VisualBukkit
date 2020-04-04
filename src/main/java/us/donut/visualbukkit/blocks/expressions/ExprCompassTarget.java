package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The compass target of a player", "Returns: location"})
public class ExprCompassTarget extends ChangeableExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("compass target of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCompassTarget()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return arg(0) + ".setCompassTarget(" + delta + ");";
    }
}
