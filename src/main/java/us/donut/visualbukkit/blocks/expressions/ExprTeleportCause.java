package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerTeleportEvent;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A teleport cause", "Returns: teleport cause"})
public class ExprTeleportCause extends EnumBlock<PlayerTeleportEvent.TeleportCause> {
}
