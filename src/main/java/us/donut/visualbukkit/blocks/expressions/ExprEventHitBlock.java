package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The block that was hit in a ProjectileHitEvent", "Returns: block"})
public class ExprEventHitBlock extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("hit block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitBlock()";
    }
}
