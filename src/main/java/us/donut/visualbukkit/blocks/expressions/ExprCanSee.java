package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"Checks if a player can see another player", "Returns: booleab"})
public class ExprCanSee extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Player.class, new ChoiceParameter("can", "cannot"), "see", Player.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".canSee(" + arg(1) + ")";
    }
}
