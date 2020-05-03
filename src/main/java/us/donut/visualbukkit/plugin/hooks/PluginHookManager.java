package us.donut.visualbukkit.plugin.hooks;

import us.donut.visualbukkit.plugin.hooks.papi.PapiHook;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PluginHookManager {

    private static Map<String, PluginHook> pluginHooks = new HashMap<>();

    static {
        pluginHooks.put("PlaceholderAPI", new PapiHook());
        pluginHooks.put("Vault", new VaultHook());
    }

    public static PluginHook getPluginHook(String pluginName) {
        return pluginHooks.get(pluginName);
    }

    public static Set<String> getPluginNames() {
        return pluginHooks.keySet();
    }
}
