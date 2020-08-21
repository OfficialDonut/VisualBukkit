package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.structures.StructEventListener;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The player involved in a PlayerEvent", "Returns: player"})
public class ExprEventPlayer extends ExpressionBlock<Player> {

    @Override
    protected Syntax init() {
        return new Syntax("event player");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerEvent.class, BlockBreakEvent.class, BlockCanBuildEvent.class, BlockDamageEvent.class,
                BlockPlaceEvent.class, SignChangeEvent.class, InventoryCloseEvent.class, InventoryOpenEvent.class);
    }

    @Override
    public String toJava() {
        return InventoryEvent.class.isAssignableFrom(((StructEventListener) getStatement().getStructure()).getEvent()) ?
                "((Player) event.getPlayer())" :
                "event.getPlayer()";
    }
}
