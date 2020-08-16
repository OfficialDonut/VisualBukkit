package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A resource pack status", "Returns: status"})
public class ExprResourcePackStatus extends EnumBlock<PlayerResourcePackStatusEvent.Status> {
}
