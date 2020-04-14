package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.World;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The spawn location of a world", "Returns: location"})
public class ExprSpawnLocation extends ChangeableExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("spawn location of", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSpawnLocation()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setSpawnLocation(" + delta + ");" : null;
    }
}
