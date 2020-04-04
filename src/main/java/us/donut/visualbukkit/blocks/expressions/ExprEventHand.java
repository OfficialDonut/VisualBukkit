package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The hand used in a PlayerInteractEvent", "Returns: equipment slot"})
@Event(PlayerInteractEvent.class)
public class ExprEventHand extends ExpressionBlock<EquipmentSlot> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event hand");
    }

    @Override
    public String toJava() {
        return "event.getHand()";
    }
}
