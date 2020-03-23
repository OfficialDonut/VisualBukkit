package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Particle;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A particle", "Returns: particle"})
public class ExprParticle extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return Particle.class;
    }
}
