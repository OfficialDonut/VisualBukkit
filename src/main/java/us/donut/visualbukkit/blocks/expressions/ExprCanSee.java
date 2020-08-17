package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a player can see another player", "Returns: boolean"})
public class ExprCanSee extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Player.class, new BinaryChoiceParameter("can", "cannot"), "see", Player.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".canSee(" + arg(2) + ")";
    }
}
