package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The vehicle of an entity", "Returns: entity"})
public class ExprEntityVehicle extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("vehicle of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVehicle()";
    }

    @Override
    public Class<?> getReturnType() {
        return Entity.class;
    }
}
