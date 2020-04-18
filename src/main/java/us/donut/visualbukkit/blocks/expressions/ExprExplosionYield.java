package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The percentage of blocks to drop in a BlockExplodeEvent or EntityExplodeEvent", "Changers: set, add, remove", "Returns: number"})
@Event({BlockExplodeEvent.class, EntityExplodeEvent.class})
public class ExprExplosionYield extends ChangeableExpressionBlock<Float> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("explosion yield");
    }

    @Override
    public String toJava() {
        return "event.getYield()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "event.setYield(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
