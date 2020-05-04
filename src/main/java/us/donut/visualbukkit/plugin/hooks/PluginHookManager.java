package us.donut.visualbukkit.plugin.hooks;

import us.donut.visualbukkit.plugin.hooks.papi.PapiHook;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PluginHookManager {

    private static Map<String, PluginHook> pluginHooks = new LinkedHashMap<>();

    static {
        pluginHooks.put("PlaceholderAPI", new PapiHook());
        pluginHooks.put("Vault", new SimplePluginHook(VaultHook.class));
        pluginHooks.put("WorldGuard", new SimplePluginHook(WorldGuardHook.class));
    }

    public static PluginHook getPluginHook(String pluginName) {
        return pluginHooks.get(pluginName);
    }

    public static Set<String> getPluginNames() {
        return pluginHooks.keySet();
    }
}
