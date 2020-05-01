package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The player of a skull block", "Changers: set", "Returns: offline player"})
public class ExprSkullBlockPlayer extends ChangeableExpressionBlock<OfflinePlayer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("player of skull", Skull.class);
    }

    @Override
    public String toJava() {
        return  arg(0) + ".getOwningPlayer()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setOwningPlayer(" + delta + ");" : null;
    }
}
