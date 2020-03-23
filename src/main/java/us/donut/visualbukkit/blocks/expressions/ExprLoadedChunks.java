package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The loaded chunks of a world", "Returns: list of chunks"})
public class ExprLoadedChunks extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("loaded chunks of", World.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getLoadedChunks())";
    }

    @Override
    public Class<?> getReturnType() {
        return SimpleList.class;
    }
}
