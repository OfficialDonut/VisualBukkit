package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;

@Name("Event GUI Clicked Slot")
@Description("The clicked slot in a GUIClickEvent")
public class ExprEventGUIClickedSlot extends ExpressionBlock<Integer> {

    public ExprEventGUIClickedSlot() {
        init("GUI clicked slot");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI clicked slot must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getInventoryClickEvent().getSlot()";
    }
}
