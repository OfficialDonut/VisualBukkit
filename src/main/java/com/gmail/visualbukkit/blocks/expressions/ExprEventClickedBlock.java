package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

@Description("The clicked block in a PlayerInteractEvent")
public class ExprEventClickedBlock extends ExpressionBlock<Block> {

    public ExprEventClickedBlock() {
        init("clicked block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Clicked block must be used in a PlayerInteractEvent", PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClickedBlock()";
    }
}