package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;

public class PluginModuleRegistry {

    private static final Set<PluginModule> pluginModules = new TreeSet<>();

    public static void register(PluginModule module) {
        pluginModules.add(module);
    }

    public static Set<PluginModule> getPluginModules() {
        return pluginModules;
    }

    static {
        register(new PluginModule("gui-manager", "GUI Manager") {
            @Override
            public void enable() {
                BlockRegistry.register(PluginModuleRegistry.class.getClassLoader(), "com.gmail.visualbukkit.blocks.definitions.gui");
            }
            @Override
            public void prepareBuild(BuildInfo buildInfo) {
                buildInfo.addClass(loadClass("GUIManager.java"));
                buildInfo.addClass(loadClass("GUIIdentifier.java"));
                buildInfo.addClass(loadClass("GUIClickEvent.java"));
                MethodSource<JavaClassSource> enableMethod = buildInfo.getMainClass().getMethod("onEnable");
                enableMethod.setBody(enableMethod.getBody() + "Bukkit.getPluginManager().registerEvents(GUIManager.getInstance(), this);");
            }
        });
    }

    private static JavaClassSource loadClass(String file) {
        try (InputStream inputStream = PluginModuleRegistry.class.getResourceAsStream("/plugin/classes/" + file)) {
            return Roaster.parse(JavaClassSource.class, inputStream);
        } catch (IOException e) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to load class", e);
            return null;
        }
    }
}
