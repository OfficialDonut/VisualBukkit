package com.gmail.visualbukkit.project;

import java.util.Set;
import java.util.TreeSet;

public class PluginModuleRegistry {

    private static final Set<PluginModule> pluginModules = new TreeSet<>();

    public static void register(PluginModule module) {
        pluginModules.add(module);
    }

    public static Set<PluginModule> getPluginModules() {
        return pluginModules;
    }
}
