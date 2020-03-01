package us.donut.visualbukkit.blocks;

import com.google.common.base.CaseFormat;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang.ClassUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.util.SimpleList;

import java.io.File;
import java.util.*;
import java.util.function.Function;

public class TypeHandler {

    private static BiMap<Class<?>, String> types = HashBiMap.create();
    private static Map<String, Function<String, String>> stringParsers = new HashMap<>();

    static {
        register(Boolean.class, "boolean", s -> "Boolean.valueOf(" + s + ")");
        register(Number.class, "number", s -> "Double.valueOf(" + s + ")");
        register(OfflinePlayer.class, "offline player", s -> "Bukkit.getOfflinePlayer(" + s + ")");
        register(Player.class, "player", s -> "Bukkit.getPlayer(" + s + ")");

        register(Block.class, "block");
        register(Entity.class, "entity");
        register(File.class, "file");
        register(ItemStack.class, "itemstack");
        register(LivingEntity.class, "living entity");
        register(Location.class, "location");
        register(Object.class, "object");
        register(SimpleList.class, "list");
        register(String.class, "string");
    }

    public static void register(Class<?> clazz, String alias, Function<String, String> stringParser) {
        stringParsers.put(alias, stringParser);
        register(clazz, alias);
    }

    public static void register(Class<?> clazz, String alias) {
        types.put(clazz, alias);
    }

    public static String convert(Class<?> from, Class<?> to, String src) {
        if (to.isAssignableFrom(from) || to == Void.class) {
            return src;
        }
        if (isPrimitiveNumber(from) && isPrimitiveNumber(to)) {
            return "((" + to.getSimpleName() + ")" + src + ")";
        }
        if (to.isPrimitive() && from == ClassUtils.primitiveToWrapper(to)) {
            return src + "." + to.getSimpleName() + "Value()";
        }
        if (Number.class.isAssignableFrom(from) && isPrimitiveNumber(to)) {
            return src + "." + to.getSimpleName() + "Value()";
        }
        if (isPrimitiveNumber(from) && to == Number.class) {
            return boxPrimitive(from, src);
        }
        if (from.isPrimitive() && to == ClassUtils.primitiveToWrapper(from)) {
            return boxPrimitive(from, src);
        }
        if (from.isPrimitive() && to == Object.class) {
            return boxPrimitive(from, src);
        }
        if (from.isAssignableFrom(to) || from == Void.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (from == Object.class && to.isPrimitive()) {
            Class<?> wrapper = ClassUtils.primitiveToWrapper(to);
            return convert(wrapper, to, "((" + wrapper.getCanonicalName() + ")" + src + ")");
        }
        throw new UnsupportedOperationException();
    }

    private static String boxPrimitive(Class<?> primitive, String src) {
        Class<?> wrapper = ClassUtils.primitiveToWrapper(primitive);
        return "new " + wrapper.getCanonicalName() + "(" + src + ")";
    }

    public static boolean isNumber(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz) || isPrimitiveNumber(clazz);
    }

    public static boolean isPrimitiveNumber(Class<?> clazz) {
        return clazz.isPrimitive() && clazz != boolean.class && clazz != char.class;
    }

    public static String getUserFriendlyName(Class<?> clazz) {
        String name = getAlias(clazz);
        if (name != null) {
            return name;
        }
        if (isNumber(clazz)) {
            return "number";
        }
        if (clazz == Class.class) {
            return "type";
        }
        name = clazz.getSimpleName();
        if (name.toUpperCase().equals(name)) {
            return name;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name).replace("_", " ");
    }

    public static Set<String> getAliases() {
        return types.values();
    }

    public static String getAlias(Class<?> type) {
        return types.get(type);
    }

    public static Class<?> getType(String alias) {
        return types.inverse().get(alias);
    }

    public static Map<String, Function<String, String>> getStringParsers() {
        return stringParsers;
    }
}
