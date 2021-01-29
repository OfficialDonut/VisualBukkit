package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.EnderCrystal;

@Description("Sets the visibility of the bedrock slate below an end crystal")
public class StatSetEndCrystalBedrockVisibility extends StatementBlock {

    public StatSetEndCrystalBedrockVisibility() {
        init("set bedrock visibility of ", EnderCrystal.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setShowingBottom(" + arg(1) + ");";
    }
}
