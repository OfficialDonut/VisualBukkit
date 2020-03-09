package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The type of an entity", "Returns: entity type"})
public class ExprTypeOfEntity extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("type of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }

    @Override
    public Class<?> getReturnType() {
        return EntityType.class;
    }
}
