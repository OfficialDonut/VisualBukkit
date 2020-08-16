package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked block face in a PlayerInteractEvent", "Returns: block face"})
public class ExprEventClickedBlockFace extends ExpressionBlock<BlockFace> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked block face");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBlockFace()";
    }
}
