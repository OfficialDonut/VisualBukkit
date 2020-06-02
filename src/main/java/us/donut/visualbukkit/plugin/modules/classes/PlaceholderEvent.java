package us.donut.visualbukkit.plugin.modules.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlaceholderEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private String identifier;
    private String result;

    public PlaceholderEvent(Player player, String identifier) {
        super(player);
        this.player = player;
        this.identifier = identifier;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getResult() {
        return result;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
