package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The block involved in an event", "Returns: block"})
public class ExprEventBlock extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("event block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(BlockEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBlock()";
    }
}
