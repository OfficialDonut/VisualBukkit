package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The loaded chunks of a world", "Returns: list of chunks"})
public class ExprLoadedChunks extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("loaded chunks of", World.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getLoadedChunks())";
    }
}
