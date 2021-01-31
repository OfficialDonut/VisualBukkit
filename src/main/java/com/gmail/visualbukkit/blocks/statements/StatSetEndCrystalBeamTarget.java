package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;

@Description("Sets the location to which an end crystal points")
public class StatSetEndCrystalBeamTarget extends StatementBlock {

    public StatSetEndCrystalBeamTarget() {
        init("set beam target of ", EnderCrystal.class, " to ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setBeamTarget(" + arg(1) + ");";
    }
}
