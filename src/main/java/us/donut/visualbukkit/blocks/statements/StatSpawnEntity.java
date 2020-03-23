package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description("Spawns an entity")
public class StatSpawnEntity extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("spawn", EntityType.class, "at", Location.class);
    }

    @Override
    public String toJava() {
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(1) + ";" +
                locVar + ".getWorld().spawnEntity(" + locVar + "," + arg(0) + ");";
    }
}
