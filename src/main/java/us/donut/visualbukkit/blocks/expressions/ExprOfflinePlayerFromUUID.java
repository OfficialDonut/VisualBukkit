package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.UUID;

@Name("Offline Player From UUID")
@Description({"The offline player with the given UUID", "Returns: offline player"})
public class ExprOfflinePlayerFromUUID extends ExpressionBlock<OfflinePlayer> {

    @Override
    protected Syntax init() {
        return new Syntax("offline player from", UUID.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getOfflinePlayer(" + arg(0) + ")";
    }
}
