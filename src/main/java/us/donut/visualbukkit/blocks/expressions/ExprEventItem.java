package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The item in a PlayerInteractEvent", "Returns: item stack"})
@Event(PlayerInteractEvent.class)
public class ExprEventItem extends ExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event item");
    }

    @Override
    public String toJava() {
        return "event.getItem()";
    }
}
