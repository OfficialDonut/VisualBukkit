package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.World;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The spawn location of a world", "Returns: location"})
@Modifier(ModificationType.SET)
public class ExprSpawnLocation extends ModifiableExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("spawn location of", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSpawnLocation()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setSpawnLocation(" + delta + ");" : null;
    }
}
