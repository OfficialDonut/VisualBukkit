package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The blocks that will be removed in a BlockExplodeEvent or EntityExplodeEvent", "Returns: list of blocks"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprEventExplodedBlocks extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("exploded blocks");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(BlockExplodeEvent.class, EntityExplodeEvent.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(event.blockList())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case CLEAR: return "event.blockList().clear();";
            case ADD: return "event.blockList().add(" + delta + ");";
            case REMOVE: return "event.blockList().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return Block.class;
    }
}
