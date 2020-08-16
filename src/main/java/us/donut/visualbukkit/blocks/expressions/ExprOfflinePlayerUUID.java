package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.UUID;

@Name("Offline Player UUID")
@Description({"The UUID of an offline player", "Returns: UUID"})
public class ExprOfflinePlayerUUID extends ExpressionBlock<UUID> {

    @Override
    protected Syntax init() {
        return new Syntax("UUID of", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getUniqueId()";
    }
}
