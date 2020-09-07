package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item in a PlayerInteractEvent or PlayerItemConsumeEvent", "Returns: item stack"})
public class ExprEventItem extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("event item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEvent.class, PlayerItemConsumeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItem()";
    }
}
