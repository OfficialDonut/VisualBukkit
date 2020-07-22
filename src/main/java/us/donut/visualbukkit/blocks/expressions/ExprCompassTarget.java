package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The compass target of a player", "Returns: location"})
@Modifier(ModificationType.SET)
public class ExprCompassTarget extends ModifiableExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("compass target of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCompassTarget()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setCompassTarget(" + delta + ");" : null;
    }
}
