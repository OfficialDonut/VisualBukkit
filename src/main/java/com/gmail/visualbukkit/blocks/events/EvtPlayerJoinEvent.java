package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerJoinEvent;

@Description("Called when a player joins a server")
public class EvtPlayerJoinEvent extends EventBlock {

    public EvtPlayerJoinEvent() {
        super(PlayerJoinEvent.class);
    }
}
