package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"The blocks that will be removed in a BlockExplodeEvent or EntityExplodeEvent", "Returns: list of blocks"})
@Event({BlockExplodeEvent.class, EntityExplodeEvent.class})
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprExplodedBlocks extends ModifiableExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("exploded blocks");
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
