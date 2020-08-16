package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked item in an InventoryClickEvent", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprEventClickedItem extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCurrentItem()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? "event.setCurrentItem(" + delta + ");" : null;
    }
}
