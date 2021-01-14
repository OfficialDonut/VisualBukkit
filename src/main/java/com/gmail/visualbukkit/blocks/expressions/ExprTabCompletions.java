package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.event.server.TabCompleteEvent;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The tab completions in a TabCompleteEvent (mutable list)")
@SuppressWarnings("rawtypes")
public class ExprTabCompletions extends ExpressionBlock<List> {

    public ExprTabCompletions() {
        init("tab completions");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Tab completions must be used in a TabCompleteEvent", TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCompletions()";
    }
}