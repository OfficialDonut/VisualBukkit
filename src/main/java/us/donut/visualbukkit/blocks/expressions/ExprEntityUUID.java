package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.UUID;

@Name("Entity UUID")
@Category("Entity")
@Description({"The UUID of an entity", "Returns: UUID"})
public class ExprEntityUUID extends ExpressionBlock<UUID> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("UUID of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getUniqueId()";
    }
}
