package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"Checks if an entity is dead", "Returns: boolean"})
public class ExprIsDead extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Entity.class, new ChoiceParameter("is", "is not"), "dead");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isDead()";
    }
}
