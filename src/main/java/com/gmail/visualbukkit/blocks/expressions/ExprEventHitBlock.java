package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;
import org.bukkit.event.entity.ProjectileHitEvent;

@Description("The block that was hit in a ProjectileHitEvent")
public class ExprEventHitBlock extends ExpressionBlock<Block> {

    public ExprEventHitBlock() {
        init("hit block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Hit block must be used in a ProjectileHitEvent", ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitBlock()";
    }
}