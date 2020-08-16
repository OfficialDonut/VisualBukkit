package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The type of an entity", "Returns: entity type"})
public class ExprTypeOfEntity extends ExpressionBlock<EntityType> {

    @Override
    protected Syntax init() {
        return new Syntax("type of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}
