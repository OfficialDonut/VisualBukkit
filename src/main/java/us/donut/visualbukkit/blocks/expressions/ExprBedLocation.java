package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The bed location of a player", "Returns: location"})
public class ExprBedLocation extends ExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("bed location of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBedSpawnLocation()";
    }
}
