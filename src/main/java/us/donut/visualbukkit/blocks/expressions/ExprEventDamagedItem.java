package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The damaged item in a PlayerItemDamageEvent", "Returns: item stack"})
public class ExprEventDamagedItem extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("damaged item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerItemDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItem()";
    }
}
