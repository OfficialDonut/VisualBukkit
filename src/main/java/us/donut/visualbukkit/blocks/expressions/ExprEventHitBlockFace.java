package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The entity that was hit in a ProjectileHitEvent", "Returns: block face"})
public class ExprEventHitBlockFace extends ExpressionBlock<BlockFace> {

    @Override
    protected Syntax init() {
        return new Syntax("hit block face");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitBlockFace()";
    }
}
