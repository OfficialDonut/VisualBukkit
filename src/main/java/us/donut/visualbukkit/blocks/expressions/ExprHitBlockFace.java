package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The entity that was hit in a ProjectileHitEvent", "Returns: block face"})
@Event(ProjectileHitEvent.class)
public class ExprHitBlockFace extends ExpressionBlock<BlockFace> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hit block face");
    }

    @Override
    public String toJava() {
        return "event.getHitBlockFace()";
    }
}
