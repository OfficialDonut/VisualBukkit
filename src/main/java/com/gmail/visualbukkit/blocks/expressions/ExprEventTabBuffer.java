package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.server.TabCompleteEvent;

@Description("The tab buffer in a TabCompleteEvent")
public class ExprEventTabBuffer extends ExpressionBlock<String> {

    public ExprEventTabBuffer() {
        init("tab buffer");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Tab buffer must be used in a TabCompleteEvent", TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBuffer()";
    }
}