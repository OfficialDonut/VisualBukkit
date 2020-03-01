package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"Checks if a player has played before", "Returns: boolean"})
public class ExprHasPlayedBefore extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(OfflinePlayer.class, new ChoiceParameter("has", "has not"), "played before");
    }

    @Override
    public String toJava() {
        return (isNegated() ? "!" : "") + arg(0) + ".hasPlayedBefore()";
    }
}
