package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The compass target of a player", "Returns: location"})
@Modifier(ModificationType.SET)
public class ExprCompassTarget extends ExpressionBlock<Location> {

    @Override
    protected Syntax init() {
        return new Syntax("compass target of", Player.class);
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
