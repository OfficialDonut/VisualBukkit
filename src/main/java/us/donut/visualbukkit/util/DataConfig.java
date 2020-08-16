package us.donut.visualbukkit.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataConfig extends YamlConfiguration {

    public DataConfig() {}

    public DataConfig(ConfigurationSection config) {
        convertMapsToSections(config.getValues(true), this);
    }

    public List<DataConfig> getConfigList(String path) {
        List<DataConfig> configs = new ArrayList<>();
        List<?> list = getList(path);
        if (list != null) {
            for (Object o : list) {
                if (o instanceof Map) {
                    DataConfig config = new DataConfig();
                    convertMapsToSections((Map<?, ?>) o, config);
                    configs.add(config);
                } else if (o instanceof DataConfig) {
                    configs.add((DataConfig) o);
                }
            }
        }
        return configs;
    }
}
