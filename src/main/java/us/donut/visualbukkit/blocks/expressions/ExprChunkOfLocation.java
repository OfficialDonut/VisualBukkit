package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Chunk;
import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The chunk of a location", "Returns: chunk"})
public class ExprChunkOfLocation extends ExpressionBlock<Chunk> {

    @Override
    protected Syntax init() {
        return new Syntax("chunk of", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getChunk()";
    }
}
