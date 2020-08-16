package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.UUID;

@Name("Entity UUID")
@Description({"The UUID of an entity", "Returns: UUID"})
public class ExprEntityUUID extends ExpressionBlock<UUID> {

    @Override
    protected Syntax init() {
        return new Syntax("UUID of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getUniqueId()";
    }
}
