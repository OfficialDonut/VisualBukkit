package us.donut.visualbukkit.util;

import org.bukkit.configuration.ConfigurationSection;

public interface Loadable {

    void unload(ConfigurationSection section);

    void load(ConfigurationSection section) throws Exception;
}
