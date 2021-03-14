package com.gmail.visualbukkit.extensions.bstats;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;

import java.util.ResourceBundle;

public class BstatsExtension extends VisualBukkitExtension {

    public BstatsExtension() {
        BlockRegistry.register("com.gmail.visualbukkit.extensions.bstats", BstatsExtension.class.getClassLoader(), ResourceBundle.getBundle("bStatsCustomBlocks"));
    }

    @Override
    public String getName() {
        return "bStats";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
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
