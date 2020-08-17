package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an entity is dead", "Returns: boolean"})
public class ExprIsDead extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Entity.class, new BinaryChoiceParameter("is", "is not"), "dead");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isDead()";
    }
}
