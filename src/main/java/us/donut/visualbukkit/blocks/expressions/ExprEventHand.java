package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The hand used in a PlayerInteractEvent", "Returns: equipment slot"})
public class ExprEventHand extends ExpressionBlock<EquipmentSlot> {

    @Override
    protected Syntax init() {
        return new Syntax("event hand");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHand()";
    }
}
