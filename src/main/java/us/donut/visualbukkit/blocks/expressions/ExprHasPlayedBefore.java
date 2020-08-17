package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a player has played before", "Returns: boolean"})
public class ExprHasPlayedBefore extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(OfflinePlayer.class, new BinaryChoiceParameter("has", "has not"), "played before");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".hasPlayedBefore()";
    }
}
