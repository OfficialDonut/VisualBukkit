package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The player of a skull block", "Returns: offline player"})
@Modifier(ModificationType.SET)
public class ExprSkullBlockPlayer extends ExpressionBlock<OfflinePlayer> {

    @Override
    protected Syntax init() {
        return new Syntax("player of skull", Skull.class);
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
