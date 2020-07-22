package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The player of a skull block", "Returns: offline player"})
@Modifier(ModificationType.SET)
public class ExprSkullBlockPlayer extends ModifiableExpressionBlock<OfflinePlayer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("player of skull", Skull.class);
    }

    @Override
    public String toJava() {
        return  arg(0) + ".getOwningPlayer()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setOwningPlayer(" + delta + ");" : null;
    }
}
