package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an entity is on the ground", "Returns: boolean"})
public class ExprIsOnGround extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Entity.class, new BinaryChoiceParameter("is", "is not"), "on ground");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isOnGround()";
    }
}
