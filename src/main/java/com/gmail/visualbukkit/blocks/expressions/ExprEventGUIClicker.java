package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.stdlib.GUIClickEvent;
import org.bukkit.entity.Player;

@Name("Event GUI Clicker")
@Description("The player who clicked the GUI in a GUIClickEvent")
public class ExprEventGUIClicker extends ExpressionBlock<Player> {

    public ExprEventGUIClicker() {
        init("GUI clicker");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("GUI clicker must be used in a GUIClickEvent", GUIClickEvent.class);
    }

    @Override
    public String toJava() {
        return "((Player) event.getInventoryClickEvent().getWhoClicked())";
    }
}
