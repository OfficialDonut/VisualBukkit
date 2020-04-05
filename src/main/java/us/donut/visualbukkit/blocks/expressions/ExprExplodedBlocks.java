package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The blocks that will be removed in a BlockExplodeEvent or EntityExplodeEvent", "Returns: list of blocks"})
@Event({BlockExplodeEvent.class, EntityExplodeEvent.class})
public class ExprExplodedBlocks extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("exploded blocks");
    }

    @Override
    public String toJava() {
        return "event.blockList()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case ADD: return "event.blockList().add(" + delta + ");";
            case REMOVE: return "event.blockList().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return Block.class;
    }
}
