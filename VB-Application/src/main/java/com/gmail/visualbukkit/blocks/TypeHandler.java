package com.gmail.visualbukkit.blocks;

import org.apache.commons.lang.ClassUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attributable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.metadata.Metadatable;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.projectiles.ProjectileSource;

import java.util.*;

public class TypeHandler {

    private static Set<ResourceBundle> resourceBundles = new HashSet<>();
    private static Map<Class<?>, String> nameCache = new HashMap<>();

    public static boolean canConvert(Class<?> from, Class<?> to) {
        return convert(from, to, "") != null;
    }

    public static String convert(Class<?> from, Class<?> to, String src) {
        if (to.isAssignableFrom(from) || to == Object.class) {
            return src;
        }
        if (from == void.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (to == String.class) {
            return "String.valueOf(" + src + ")";
        }
        if (from == String.class && to == char.class) {
            return src + ".charAt(0)";
        }
        if (List.class.isAssignableFrom(from) && to.isArray()) {
            return src + ".toArray(new " + to.getCanonicalName() + "{})";
        }

        if (to.isPrimitive() && from == ClassUtils.primitiveToWrapper(to)) {
            return src;
        }
        if (from.isPrimitive() && to == ClassUtils.primitiveToWrapper(from)) {
            return src;
        }
        if (isPrimitiveNumber(from) && to == Number.class) {
            return src;
        }
        if (isPrimitiveNumber(from) && Number.class.isAssignableFrom(to)) {
            return "((" + ClassUtils.wrapperToPrimitive(to).getCanonicalName() + ")" + src + ")";
        }
        if (Number.class.isAssignableFrom(from) && isPrimitiveNumber(to)) {
            return src + "." + to.getCanonicalName() + "Value()";
        }
        if (isPrimitiveNumber(from) && isPrimitiveNumber(to)) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }

        if (from.isAssignableFrom(to) || (from == Object.class && to.isPrimitive())) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }

        if (from == OfflinePlayer.class && to == Player.class) {
            return src + ".getPlayer()";
        }
        if (CommandSender.class.isAssignableFrom(from) && (to == OfflinePlayer.class || to == ProjectileSource.class || to == Metadatable.class || to == PersistentDataHolder.class)) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if ((CommandSender.class.isAssignableFrom(from) || Entity.class.isAssignableFrom(from)) && to == Attributable.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (Entity.class.isAssignableFrom(from) && to == Sittable.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }

        return null;
    }

    public static boolean isNumber(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz) || isPrimitiveNumber(clazz);
    }

    public static boolean isPrimitiveNumber(Class<?> clazz) {
        return clazz.isPrimitive() && clazz != boolean.class && clazz != char.class;
    }

    public static boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz) || Iterator.class.isAssignableFrom(clazz) || clazz.isArray();
    }

    public static String getUserFriendlyName(Class<?> clazz) {
        String name = getResourceString(clazz.getName());
        if (name != null) {
            return name;
        }

        if (clazz == void.class) {
            return getUserFriendlyName(Object.class);
        }

        if (isNumber(clazz)) {
            return getResourceString("number");
        }

        if (isCollection(clazz)) {
            return getResourceString("collection");
        }

        return nameCache.computeIfAbsent(clazz, k -> {
            String str = k.getSimpleName();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                builder.append(c);
                if (i + 1 < str.length()
                        && Character.isUpperCase(str.charAt(i + 1))
                        && (Character.isLowerCase(c)
                        || (i + 2 < str.length() && Character.isLowerCase(str.charAt(i + 2))))) {
                    builder.append(' ');
                }
            }
            return builder.toString();
        });
    }

    private static String getResourceString(String key) {
        for (ResourceBundle resourceBundle : resourceBundles) {
            if (resourceBundle.containsKey(key)) {
                return resourceBundle.getString(key);
            }
        }
        return null;
    }

    public static void registerResourceBundle(ResourceBundle resourceBundle) {
        resourceBundles.add(resourceBundle);
    }
}
