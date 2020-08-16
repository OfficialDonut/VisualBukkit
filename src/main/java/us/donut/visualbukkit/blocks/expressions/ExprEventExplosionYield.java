package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The percentage of blocks to drop in a BlockExplodeEvent or EntityExplodeEvent", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprEventExplosionYield extends ExpressionBlock<Float> {

    @Override
    protected Syntax init() {
        return new Syntax("explosion yield");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(BlockExplodeEvent.class, EntityExplodeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getYield()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return "event.setYield(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
