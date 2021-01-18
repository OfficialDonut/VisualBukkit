package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

import java.util.List;

@Description("The loaded chunks of a world")
@SuppressWarnings("rawtypes")
public class ExprLoadedChunks extends ExpressionBlock<List> {

    public ExprLoadedChunks() {
        init("loaded chunks of ", World.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getLoadedChunks())";
    }
}