package us.donut.visualbukkit.plugin.modules.classes;

import us.donut.visualbukkit.plugin.PluginMain;

public class ExpansionHandler {
    
    public static void register(PluginMain plugin) {
        new PapiExpansion(plugin);
    }
}
