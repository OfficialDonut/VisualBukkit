package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.CreatureSpawnEvent;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A spawn reason", "Returns: spawn reason"})
public class ExprSpawnReason extends EnumBlock<CreatureSpawnEvent.SpawnReason> {
}
