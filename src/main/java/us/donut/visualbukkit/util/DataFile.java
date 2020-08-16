package us.donut.visualbukkit.util;

import org.bukkit.configuration.InvalidConfigurationException;
import us.donut.visualbukkit.VisualBukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;

public class DataFile extends DataConfig {

    private File file;

    public DataFile(Path path) {
        this.file = path.toFile();
        if (file.exists()) {
            try {
                load(file);
            } catch (IOException | InvalidConfigurationException e) {
                VisualBukkit.displayException("Failed to load data file", e);
            }
        }
    }

    public void save() throws IOException {
        save(file);
    }

    public void clear() {
        for (String key : new HashSet<>(getKeys(false))) {
            set(key, null);
        }
    }
}
