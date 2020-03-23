package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The clicked block face in a PlayerInteractEvent", "Returns: block face"})
@Event(PlayerInteractEvent.class)
public class ExprClickedBlockFace extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clicked block face");
    }

    @Override
    public String toJava() {
        return "event.getBlockFace()";
    }

    @Override
    public Class<?> getReturnType() {
        return BlockFace.class;
    }
}
