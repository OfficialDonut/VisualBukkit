import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PluginMain extends JavaPlugin implements Listener {

    private static PluginMain instance;

    public static PluginMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
        return true;
    }

    public static void procedure(String procedure, List args) throws Exception {}

    public static Object function(String function, List args) throws Exception {
        return null;
    }

    public static char resolve_char(Object o) {
        return o instanceof String s ? s.charAt(0) : (char) o;
    }

    public static boolean resolve_boolean(Object o) {
        return (boolean) o;
    }

    public static byte resolve_byte(Object o) {
        return ((Number) o).byteValue();
    }

    public static short resolve_short(Object o) {
        return ((Number) o).shortValue();
    }

    public static int resolve_int(Object o) {
        return ((Number) o).intValue();
    }

    public static long resolve_long(Object o) {
        return ((Number) o).longValue();
    }

    public static float resolve_float(Object o) {
        return ((Number) o).floatValue();
    }

    public static double resolve_double(Object o) {
        return ((Number) o).doubleValue();
    }

    public static <T> T resolve_object(Object from, Class<T> to) {
        if (from == null) {
            return null;
        }
        if (to.isAssignableFrom(from.getClass())) {
            return to.cast(from);
        }
        if (from instanceof Number num && Number.class.isAssignableFrom(to)) {
            return to.cast(num.doubleValue());
        }
        if (from instanceof Collection collection && to.isArray()) {
            Object arr = Array.newInstance(to.componentType(), collection.size());
            int i = 0;
            for (Object obj : collection) {
                Array.set(arr, i++, obj);
            }
            return (T) arr;
        }
        if (from instanceof Collection collection && Collection.class.isAssignableFrom(to)) {
            Collection newCollection = getCollectionInstance(to);
            newCollection.addAll(collection);
            return (T) newCollection;
        }
        if (from.getClass().isArray() && Collection.class.isAssignableFrom(to)) {
            Collection newCollection = getCollectionInstance(to);
            for (int i = 0; i < Array.getLength(from); i++) {
                newCollection.add(Array.get(from, i));
            }
            return (T) newCollection;
        }
        return to.cast(from);
    }

    private static Collection getCollectionInstance(Class<?> type) {
        try {
            return (Collection) type.getConstructor().newInstance();
        } catch (Exception e) {
            if (List.class.isAssignableFrom(type)) {
                return new ArrayList<>();
            }
            if (Set.class.isAssignableFrom(type)) {
                return new HashSet<>();
            }
            if (Queue.class.isAssignableFrom(type)) {
                return new ArrayDeque<>();
            }
            return null;
        }
    }

    private static void createResourceFile(String path) {
        Path file = getInstance().getDataFolder().toPath().resolve(path);
        if (Files.notExists(file)) {
            try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
                Files.createDirectories(file.getParent());
                Files.copy(inputStream, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}