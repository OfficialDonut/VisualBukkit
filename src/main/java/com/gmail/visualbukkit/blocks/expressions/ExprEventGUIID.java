package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;

@Name("Event GUI ID")
@Description("The ID of the GUI in a GUIClickEvent")
public class ExprEventGUIID extends ExpressionBlock<String> {

    public ExprEventGUIID() {
        init("GUI ID");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI ID must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getID()";
    }
}
