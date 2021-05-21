package com.gmail.visualbukkit.extensions.bstats;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class BstatsExtension extends VisualBukkitExtension {

    protected static String METRICS_CLASS;

    public BstatsExtension() throws IOException {
        try (InputStream inputStream = BstatsExtension.class.getResourceAsStream("/Metrics.java")) {
            METRICS_CLASS = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            BlockRegistry.register(this, "com.gmail.visualbukkit.extensions.bstats", ResourceBundle.getBundle("CustomBstatsBlocks"));
        }
    }

    @Override
    public String getName() {
        return "bStats";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Donut";
    }

    @Override
    public String getDescription() {
        return "Adds support for bStats";
    }
}
