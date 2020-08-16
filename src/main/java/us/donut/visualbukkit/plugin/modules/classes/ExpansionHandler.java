package us.donut.visualbukkit.plugin.modules.classes;

import org.bukkit.plugin.java.JavaPlugin;

public class ExpansionHandler {
    
    public static void register(JavaPlugin plugin) {
        new PapiExpansion(plugin).register();
    }
}
