package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The click type in an InventoryClickEvent", "Returns: click type"})
public class ExprEventClickType extends ExpressionBlock<ClickType> {

    @Override
    protected Syntax init() {
        return new Syntax("event click type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClick()";
    }
}
