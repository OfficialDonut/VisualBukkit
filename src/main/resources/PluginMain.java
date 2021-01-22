import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.*;

public class PluginMain extends JavaPlugin implements Listener {

    private static PluginMain instance;
    private static Object localVariableScope = new Object();

    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        getServer().getPluginManager().registerEvents(this, this);
        Object localVariableScope = new Object();
    }

    public void onDisable() {}

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
        return true;
    }

    public static void procedure(String procedure, List<?> procedureArgs) throws Exception {}

    public static Object function(String function, List<?> functionArgs) throws Exception {
        return null;
    }

    public static List<Object> createList(Object obj) {
        List<Object> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                list.add(java.lang.reflect.Array.get(obj, i));
            }
        } else if (obj instanceof Collection<?>) {
            list.addAll((Collection<?>) obj);
        } else {
            list.add(obj);
        }
        return list;
    }

    public static String color(String string) {
        return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
    }

    public static PluginMain getInstance() {
        return instance;
    }
}
