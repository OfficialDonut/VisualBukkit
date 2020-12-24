package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.event.server.TabCompleteEvent;

@Description("The tab sender in a TabCompleteEvent")
public class ExprEventTabSender extends ExpressionBlock<CommandSender> {

    public ExprEventTabSender() {
        init("tab sender");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Tab sender must be used in a TabCompleteEvent", TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSender()";
    }
}