package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.UUID;

@Name("Offline Player From UUID")
@Category("Player")
@Description({"The offline player with the given UUID", "Returns: offline player"})
public class ExprOfflinePlayerFromUUID extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("offline player from", UUID.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getOfflinePlayer(" + arg(0) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return OfflinePlayer.class;
    }
}
