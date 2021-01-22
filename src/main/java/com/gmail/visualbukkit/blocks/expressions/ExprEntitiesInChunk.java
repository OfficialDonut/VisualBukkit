package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Chunk;

import java.util.List;

@Description("The entities in a chunk")
public class ExprEntitiesInChunk extends ExpressionBlock<List> {

    public ExprEntitiesInChunk() {
        init("entities in ", Chunk.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getEntities())";
    }
}
