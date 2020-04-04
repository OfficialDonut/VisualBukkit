package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description({"The block involved in an event", "Returns: block"})
@Event(BlockEvent.class)
public class ExprEventBlock extends ExpressionBlock<Block> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event block");
    }

    @Override
    public String toJava() {
        return "event.getBlock()";
    }
}
