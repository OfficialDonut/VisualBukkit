package us.donut.visualbukkit.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DataFile {

    private File file;
    private YamlConfiguration config;

    public DataFile(Path path) {
        this.file = path.toFile();
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() throws IOException {
        config.save(file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
