package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked block in a PlayerInteractEvent", "Returns: block"})
public class ExprEventClickedBlock extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClickedBlock()";
    }
}
