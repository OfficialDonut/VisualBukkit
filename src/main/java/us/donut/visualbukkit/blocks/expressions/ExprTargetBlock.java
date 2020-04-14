package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The block a living entity is looking at", "Returns: block"})
public class ExprTargetBlock extends ExpressionBlock<Block> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("target block of", LivingEntity.class, "with max distance", int.class, "and fluid collision mode", FluidCollisionMode.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTargetBlockExact(" + arg(1) + ")";
    }
}
