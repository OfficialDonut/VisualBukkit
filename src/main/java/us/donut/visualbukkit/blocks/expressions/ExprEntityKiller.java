package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The player who killed a living entity", "Returns: player"})
public class ExprEntityKiller extends ExpressionBlock<Player> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("killer of", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getKiller()";
    }
}
