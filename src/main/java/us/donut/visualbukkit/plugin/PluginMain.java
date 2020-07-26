package us.donut.visualbukkit.plugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PluginMain extends JavaPlugin implements Listener {

    private static PluginMain instance;
    private static File dataFile;
    private static YamlConfiguration dataConfig;

    private static Map<String, String> stringMap = new HashMap<>();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public PluginMain() {}

    protected PluginMain(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        VariableManager.loadVariables();
        Bukkit.getScheduler().runTaskTimer(this, VariableManager::saveVariables, 0, 18000);
    }

    @Override
    public void onDisable() {
        VariableManager.saveVariables();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] commandArgs) {
        return true;
    }

    public static void procedure(String procedure, Object... args) {}

    public static Object function(String function, Object... args) {
        return null;
    }

    public static List<Object> createList(Object obj) {
        List<Object> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                list.add(Array.get(obj, i));
            }
        } else if (obj instanceof Collection<?>) {
            list.addAll((Collection<?>) obj);
        } else {
            list.add(obj);
        }
        return list;
    }

    public static String decode(String string) {
        String decoded = stringMap.get(string);
        if (decoded == null) {
            decoded = new String(decoder.decode(string), StandardCharsets.UTF_8);
            stringMap.put(string, decoded);
        }
        return decoded;
    }

    public static String color(String string) {
        return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
    }

    public static PluginMain getInstance() {
        return instance;
    }

    public static File getDataFile() {
        return dataFile;
    }

    public static YamlConfiguration getDataConfig() {
        return dataConfig;
    }
}
