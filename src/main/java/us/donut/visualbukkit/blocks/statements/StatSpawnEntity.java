package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Spawns an entity")
public class StatSpawnEntity extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("spawn", EntityType.class, "at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().spawnEntity(" + arg(1) + "," + arg(0) + ");";
    }
}
