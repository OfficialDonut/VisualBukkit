package com.gmail.visualbukkit.extensions.papi;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class PapiExtension extends VisualBukkitExtension {

    public static String EXPANSION_CLASS;
    public static String HOOK_CLASS;

    public PapiExtension() throws IOException {
        EXPANSION_CLASS = IOUtils.toString(CompRegisterPlaceholder.class.getResourceAsStream("/PapiExpansion.java"), StandardCharsets.UTF_8);
        HOOK_CLASS = IOUtils.toString(CompRegisterPlaceholder.class.getResourceAsStream("/PapiHook.java"), StandardCharsets.UTF_8);
        BlockRegistry.register("com.gmail.visualbukkit.extensions.papi", PapiExtension.class.getClassLoader(), ResourceBundle.getBundle("PapiCustomBlocks"));
    }

    @Override
    public String getName() {
        return "PlaceholderAPI";
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
        return "Adds support for PlaceholderAPI";
    }
}
