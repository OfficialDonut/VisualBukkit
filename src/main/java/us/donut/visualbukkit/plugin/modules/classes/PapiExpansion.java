package us.donut.visualbukkit.plugin.modules.classes;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.plugin.PluginMain;

public class PapiExpansion extends PlaceholderExpansion {

    private PluginMain plugin;

    public PapiExpansion(PluginMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String getIdentifier(){
        return plugin.getName().toLowerCase();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        PlaceholderEvent event = new PlaceholderEvent(player, identifier);
        Bukkit.getPluginManager().callEvent(event);
        return event.getResult();
    }
}
