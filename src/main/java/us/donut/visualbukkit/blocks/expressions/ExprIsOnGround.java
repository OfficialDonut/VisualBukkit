package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"Checks if an entity is on the ground", "Returns: boolean"})
public class ExprIsOnGround extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Entity.class, "is on ground");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isOnGround()";
    }
}
