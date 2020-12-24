package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the item in the slot that was clicked")
public class StatSetEventClickedItem extends StatementBlock {

    public StatSetEventClickedItem() {
        init("set item in clicked slot to ", ItemStack.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set clicked item must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setCurrentItem(" + arg(0) + ");";
    }
}
