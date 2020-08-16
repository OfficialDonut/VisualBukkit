package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Particle;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Spawns particles at a locations")
@Category(StatementCategory.WORLD)
public class StatSpawnParticle extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("spawn", int.class, Particle.class, "particles at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".getWorld().spawnParticle(" + arg(1) + "," + arg(2) + "," + arg(0) + ");";
    }
}
