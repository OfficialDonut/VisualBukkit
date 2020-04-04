package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The clicked block in a PlayerInteractEvent", "Returns: block"})
@Event(PlayerInteractEvent.class)
public class ExprClickedBlock extends ExpressionBlock<Block> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clicked block");
    }

    @Override
    public String toJava() {
        return "event.getClickedBlock()";
    }
}
