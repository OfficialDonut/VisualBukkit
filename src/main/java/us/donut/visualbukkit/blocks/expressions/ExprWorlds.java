package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"All worlds", "Returns: list of worlds"})
public class ExprWorlds extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("worlds");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(Bukkit.getWorlds())";
    }
}
