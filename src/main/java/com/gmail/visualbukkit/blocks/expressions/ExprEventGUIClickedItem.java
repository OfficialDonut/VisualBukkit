package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;
import org.bukkit.inventory.ItemStack;

@Name("Event GUI Clicked Item")
@Description("The clicked item in a GUIClickEvent")
public class ExprEventGUIClickedItem extends ExpressionBlock<ItemStack> {

    public ExprEventGUIClickedItem() {
        init("GUI clicked item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI clicked item must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getInventoryClickEvent().getCurrentItem()";
    }
}
