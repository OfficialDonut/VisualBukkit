package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.ProjectileHitEvent;

@Description("The block face that was hit in a ProjectileHitEvent")
public class ExprEventHitBlockFace extends ExpressionBlock<BlockFace> {

    public ExprEventHitBlockFace() {
        init("hit block face");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Hit block face must be used in a ProjectileHitEvent", ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitBlockFace()";
    }
}