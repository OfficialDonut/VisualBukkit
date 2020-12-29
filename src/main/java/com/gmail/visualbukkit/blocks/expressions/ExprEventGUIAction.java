package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;
import org.bukkit.event.inventory.InventoryAction;

@Name("Event GUI Action")
@Description("The inventory action in a GUIClickEvent")
public class ExprEventGUIAction extends ExpressionBlock<InventoryAction> {

    public ExprEventGUIAction() {
        init("GUI action");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI action must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getInventoryClickEvent().getAction()";
    }
}
