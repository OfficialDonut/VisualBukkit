package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The block that was hit in a ProjectileHitEvent", "Returns: block"})
@Event(ProjectileHitEvent.class)
public class ExprHitBlock extends ExpressionBlock<Block> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hit block");
    }

    @Override
    public String toJava() {
        return "event.getHitBlock()";
    }
}
