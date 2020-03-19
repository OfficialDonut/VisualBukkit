package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Entity")
@Description({"The entities in a world", "Returns: list of entities"})
public class ExprEntitiesInWorld extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("entities in", World.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getEntities())";
    }

    @Override
    public Class<?> getReturnType() {
        return SimpleList.class;
    }
}
