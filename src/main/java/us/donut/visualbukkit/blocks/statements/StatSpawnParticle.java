package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Particle;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Spawns particles at a locations")
public class StatSpawnParticle extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("spawn", int.class, Particle.class, "particles at", Location.class);
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod("spawnParticle");
        return "UtilMethods.spawnParticle(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
