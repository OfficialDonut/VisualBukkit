package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The players in a world", "Returns: list of players"})
public class ExprPlayersInWorld extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("players in", World.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getPlayers())";
    }
}
