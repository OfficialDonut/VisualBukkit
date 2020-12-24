package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerInteractEvent;

@Description("The clicked block face in a PlayerInteractEvent")
public class ExprEventClickedBlockFace extends ExpressionBlock<BlockFace> {

    public ExprEventClickedBlockFace() {
        init("clicked block face");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Clicked block face must be used in a PlayerInteractEvent", PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBlockFace()";
    }
}