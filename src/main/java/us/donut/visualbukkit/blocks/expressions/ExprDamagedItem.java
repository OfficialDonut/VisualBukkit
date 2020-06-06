package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Description({"The damaged item in a PlayerItemDamageEvent", "Returns: item stack"})
@Event(PlayerItemDamageEvent.class)
public class ExprDamagedItem extends ExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("damaged item");
    }

    @Override
    public String toJava() {
        if (PlayerItemDamageEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            return "event.getItem()";
        }
        throw new IllegalStateException();
    }
}
