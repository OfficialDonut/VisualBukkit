package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Particle;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Spawns particles at a locations")
public class StatSpawnParticle extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("spawn", int.class, Particle.class, "particles at", Location.class);
    }

    @Override
    public String toJava() {
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(2) + ";" +
                locVar + ".getWorld().spawnParticle(" + arg(1) + "," + locVar + "," + arg(0) + ");";
    }
}
