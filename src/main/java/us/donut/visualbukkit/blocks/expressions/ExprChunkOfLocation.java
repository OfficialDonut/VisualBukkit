package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Chunk;
import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Location")
@Description({"The chunk of a location", "Returns: chunk"})
public class ExprChunkOfLocation extends ExpressionBlock<Chunk> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("chunk of", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getChunk()";
    }
}
