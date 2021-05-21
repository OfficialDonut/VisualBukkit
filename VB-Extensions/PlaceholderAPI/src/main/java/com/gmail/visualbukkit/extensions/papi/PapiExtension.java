package com.gmail.visualbukkit.extensions.papi;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class PapiExtension extends VisualBukkitExtension {

    protected static String EXPANSION_CLASS;
    protected static String HOOK_CLASS;

    public PapiExtension() throws IOException {
        try (InputStream stream1 = PapiExtension.class.getResourceAsStream("/PapiExpansion.java");
             InputStream stream2 = PapiExtension.class.getResourceAsStream("/PapiHook.java")) {
            EXPANSION_CLASS = IOUtils.toString(stream1, StandardCharsets.UTF_8);
            HOOK_CLASS = IOUtils.toString(stream2, StandardCharsets.UTF_8);
            BlockRegistry.register(this, "com.gmail.visualbukkit.extensions.papi", ResourceBundle.getBundle("CustomPapiBlocks"));
        }
    }

    @Override
    public String getName() {
        return "PlaceholderAPI";
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
        return "Adds support for PlaceholderAPI";
    }
}
