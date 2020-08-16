package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"All online players", "Returns: list of players"})
public class ExprOnlinePlayers extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("online players");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(Bukkit.getOnlinePlayers())";
    }
}
