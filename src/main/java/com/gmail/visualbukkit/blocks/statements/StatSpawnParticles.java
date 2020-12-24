package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.Particle;

@Category(Category.WORLD)
@Description("Spawns particles at a location")
public class StatSpawnParticles extends StatementBlock {

    public StatSpawnParticles() {
        init("spawn particles");
        initLine("type:     ", Particle.class);
        initLine("number:   ", int.class);
        initLine("location: ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".getWorld().spawnParticle(" + arg(0) + "," + arg(2) + "," + arg(1) + ");";
    }
}
