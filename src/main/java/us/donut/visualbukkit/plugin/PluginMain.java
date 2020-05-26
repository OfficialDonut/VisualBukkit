package us.donut.visualbukkit.plugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PluginMain extends JavaPlugin implements Listener {

    private static PluginMain instance;
    private static File dataFile;
    private static YamlConfiguration dataConfig;

    public PluginMain() {
        super();
    }

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

    public static String decode(String string) {
        return new String(Base64.getDecoder().decode(string), StandardCharsets.UTF_8);
    }

    public static boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
    }

    public static String getString(Object object) {
        if (object == null) {
            return "null";
        }
        if (object instanceof OfflinePlayer) {
            return ((OfflinePlayer) object).getName();
        }
        if (object instanceof World) {
            return ((World) object).getName();
        }
        if (object instanceof Entity) {
            return ((Entity) object).getType().toString();
        }
        return object.toString();
    }

    public static String color(String string) {
        return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
    }

    public static List<String> color(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            strings.add(i, color(strings.get(i)));
            strings.remove(i + 1);
        }
        return strings;
    }

    public static ShapedRecipe getShapedRecipe(ItemStack result, Material... ingredients) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getInstance(), UUID.randomUUID().toString()), result);
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                if (i < 3) {
                    recipe.shape("012");
                } else if (i < 6) {
                    recipe.shape("012", "345");
                } else {
                    recipe.shape("012", "345", "678");
                }
            }
        }
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                recipe.setIngredient(Character.forDigit(i, 10), ingredient);
            }
        }
        return recipe;
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
