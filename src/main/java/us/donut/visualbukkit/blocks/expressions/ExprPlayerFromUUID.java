package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.UUID;

@Name("Player From UUID")
@Category("Player")
@Description({"The player with the given UUID", "Returns: player"})
public class ExprPlayerFromUUID extends ExpressionBlock<Player> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("player from", UUID.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getPlayer(" + arg(0) + ")";
    }
}
