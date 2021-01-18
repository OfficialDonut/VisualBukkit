package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("All online players")
@SuppressWarnings("rawtypes")
public class ExprOnlinePlayers extends ExpressionBlock<List> {

    public ExprOnlinePlayers() {
        init("online players");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(Bukkit.getOnlinePlayers())";
    }
}