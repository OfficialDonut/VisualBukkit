package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;
import org.bukkit.event.inventory.ClickType;

@Name("Event GUI Click Type")
@Description("The click type in a GUIClickEvent")
public class ExprEventGUIClickType extends ExpressionBlock<ClickType> {

    public ExprEventGUIClickType() {
        init("GUI click type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI click type must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getInventoryClickEvent().getClick()";
    }
}
