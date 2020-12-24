package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Chunk;
import org.bukkit.Location;

@Description("The chunk of a location")
public class ExprChunkOfLocation extends ExpressionBlock<Chunk> {

    public ExprChunkOfLocation() {
        init("chunk of ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getChunk()";
    }
}